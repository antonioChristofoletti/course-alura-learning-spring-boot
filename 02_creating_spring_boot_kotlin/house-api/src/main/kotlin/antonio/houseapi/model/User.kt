package antonio.houseapi.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import org.springframework.security.core.userdetails.UserDetails

@Entity
open class User: UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    open var name: String = ""

    open var userPass: String = ""

    open var email: String = ""

    @ManyToMany(targetEntity = Profile::class, fetch = FetchType.EAGER)
    open var profiles: List<Profile> = listOf()

    override fun getAuthorities() = profiles
    override fun getPassword() = userPass
    override fun getUsername() = email

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}