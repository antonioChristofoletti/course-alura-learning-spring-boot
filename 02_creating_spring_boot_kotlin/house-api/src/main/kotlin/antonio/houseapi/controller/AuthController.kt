package antonio.houseapi.controller

import antonio.houseapi.config.security.TokenService
import antonio.houseapi.dto.DtoToken
import antonio.houseapi.dto.DtoUserLogin
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (
    @Autowired
    val authManager: AuthenticationManager,
    @Autowired
    val tokenService: TokenService
) {
    @PostMapping
    fun auth(@RequestBody @Valid dtoUserLogin:  DtoUserLogin): ResponseEntity<DtoToken> {
        val userNamePasswordAuthenticationToken = dtoUserLogin.getUserNamePasswordAuthenticationToken()
        val responseEntityError = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DtoToken())

        return try {
            val authentication = authManager.authenticate(userNamePasswordAuthenticationToken) ?: return responseEntityError
            val token = tokenService.generateToken(authentication) ?: return responseEntityError

            ResponseEntity.ok(DtoToken(token, "Bearer"))
        } catch (e: AuthenticationException) {
            ResponseEntity.badRequest().build()
        }
    }
}