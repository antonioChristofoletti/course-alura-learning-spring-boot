package antonio.houseapi.config.security

import antonio.houseapi.repository.UserRepository
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfiguration(
    @Autowired
    val tokenService: TokenService,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val authService: AuthService
): WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    //Configuracoes de autenticacao
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authService)?.passwordEncoder(BCryptPasswordEncoder())
    }

    //Configuracoes de autorizacao
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/person").permitAll()
            .antMatchers(HttpMethod.GET, "/person/*").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(
                AuthTokenFilter(tokenService, userRepository),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }

    //Configuracoes de recursos estáticos(js, css, imagens, etc.)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/**.html",
            "/v2/api-docs",
            "/webjars/**",
            "/configuration/**",
            "/swagger-resources/**"
        )
    }
}