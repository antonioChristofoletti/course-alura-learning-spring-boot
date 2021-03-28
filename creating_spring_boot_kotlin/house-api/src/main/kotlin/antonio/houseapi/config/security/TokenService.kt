package antonio.houseapi.config.security

import antonio.houseapi.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import javax.crypto.SecretKey
import io.jsonwebtoken.io.Encoder
import io.jsonwebtoken.io.Encoders

@Service
class TokenService {

    @Value("\${jwt.expiration}")
    lateinit var expiration: String

    @Value("\${jwt.secret}")
    lateinit var secret: String

    private val privateKey by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)) }

    private fun getJwtParser() = Jwts.parserBuilder().setSigningKey(this.privateKey).build()

    fun getIdUserFromToken(token: String) = getJwtParser().parseClaimsJws(token).body.subject?.toLong()

    fun generateToken(auth: Authentication): String? {
        val user = auth.principal as User
        val exp = LocalDateTime.now().plus(this.expiration.toLong(), ChronoUnit.MILLIS)

        return Jwts.builder()
            .setIssuer("House API")
            .setSubject(user.id.toString())
            .setExpiration(Date.from(exp.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(this.privateKey)
            .compact()
    }

    fun isTokenValid(token: String) = getJwtParser().isSigned(token)
}

fun main() {
    val key = Keys.secretKeyFor(SignatureAlgorithm.HS256) //or HS384 or HS512

    val secretString = Encoders.BASE64.encode(key.encoded)

    println(secretString)
}