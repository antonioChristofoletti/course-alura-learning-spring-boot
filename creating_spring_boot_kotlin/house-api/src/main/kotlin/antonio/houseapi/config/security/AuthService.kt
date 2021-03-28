package antonio.houseapi.config.security

import antonio.houseapi.Utils.CrudRepositoryUtils.Companion.getValueOrNull
import antonio.houseapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Autowired
    private val repository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {
        return repository.findByEmail(userName).getValueOrNull() ?: throw UsernameNotFoundException("User's data is invalid")
    }
}