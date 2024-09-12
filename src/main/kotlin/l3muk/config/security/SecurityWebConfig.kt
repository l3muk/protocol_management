package l3muk.config.security

import l3muk.service.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityWebConfig(
  private val userDetailsService: UserServiceImpl
) {

  @Bean
  fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
    authConfig.authenticationManager

  @Bean
  fun configureGlobal(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder())
  }

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http {
      csrf { disable() }
      authorizeHttpRequests {
        authorize(anyRequest, permitAll)
      }
      httpBasic { }
    }

    return http.build()
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder =
    BCryptPasswordEncoder();

}