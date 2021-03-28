package antonio.houseapi.dto

import com.sun.istack.NotNull
import javax.validation.constraints.NotEmpty
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

open class DtoUserLogin {
    @NotNull @NotEmpty
    open var password: String = ""

    @NotNull @NotEmpty
    open var email: String = ""

    fun getUserNamePasswordAuthenticationToken(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(this.email, this.password)
    }
}