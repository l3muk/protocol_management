package l3muk.service

import l3muk.dto.UserCreateRequest
import l3muk.exception.DuplicateUsernameException
import l3muk.model.User
import l3muk.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface UserService {
  fun createUser(user: UserCreateRequest): User
}

@Service
class UserServiceImpl(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder
) : UserService, UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails =
    userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)

  override fun createUser(user: UserCreateRequest): User =
    userRepository.findByUsername(user.username)?.let {
      throw DuplicateUsernameException("the username ${user.username} is unavailable")
    } ?: userRepository.save(user.toModel())

  private fun UserCreateRequest.toModel() = User(
    username = username,
    password = passwordEncoder.encode(password)
  )

}