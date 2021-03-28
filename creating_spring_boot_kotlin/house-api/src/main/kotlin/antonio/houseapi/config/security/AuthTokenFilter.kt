package antonio.houseapi.config.security

import antonio.houseapi.Utils.CrudRepositoryUtils.Companion.getValueOrNull
import antonio.houseapi.repository.UserRepository
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class AuthTokenFilter(
    val tokenService: TokenService,
    val userRepository: UserRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getToken(httpServletRequest)

        if (!token.isNullOrEmpty() && tokenService.isTokenValid(token)) {
            authenticateUser(token)
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun authenticateUser(token: String) {
        val userID = tokenService.getIdUserFromToken(token) ?: return
        val user = userRepository.findById(userID).getValueOrNull() ?: return
        val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)

        SecurityContextHolder.getContext().authentication = auth
    }

    private fun getToken(httpServletRequest: HttpServletRequest): String? {
        return httpServletRequest.getHeader("Authorization")?.replace("Bearer", "")
    }
}
