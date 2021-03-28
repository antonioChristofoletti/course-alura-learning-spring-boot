package antonio.houseapi.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import org.springframework.security.core.GrantedAuthority

@Entity
open class Profile: GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    open var name: String = ""

    override fun getAuthority() = this.name
}