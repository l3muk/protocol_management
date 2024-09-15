package l3muk.service

import l3muk.dto.UserCreateRequest
import l3muk.dto.UserListResponse
import l3muk.dto.UserResponse
import l3muk.exception.DuplicateUsernameException
import l3muk.model.User
import l3muk.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface IUserService {
  fun createUser(user: UserCreateRequest): User
  fun getUsers(): UserListResponse
}

@Service
class IUserServiceImpl(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder
) : IUserService {

  override fun createUser(user: UserCreateRequest): User {
    if (userExists(user.username)) {
      throw DuplicateUsernameException("username is taken!")
    }
    return userRepository.save(user.toModel())
  }

  override fun getUsers(): UserListResponse {
    val users = userRepository.findAll().map { it.toResponse() }
    return UserListResponse(users, users.size)
  }

  private fun userExists(username: String) =
    userRepository.existsByUsername(username)

  private fun User.toResponse() = UserResponse(
    id = id!!,
    username = username,
    password = hashedPassword
  )

  private fun UserCreateRequest.toModel() = User(
    username = username,
    hashedPassword = passwordEncoder.encode(password)
  )
}



