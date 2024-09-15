package l3muk.controller

import l3muk.dto.UserCreateRequest
import l3muk.dto.UserListResponse
import l3muk.service.IUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

interface AuthApi {
  fun register(user: UserCreateRequest): ResponseEntity<Unit>
  fun users(): ResponseEntity<UserListResponse>
}

@RestController
@RequestMapping("/api/auth")
class AuthController(
  private val authenticationManager: AuthenticationManager,
  private val iUserService: IUserService,
  private val userDetailsService: UserDetailsService
) : AuthApi {

  @PostMapping("register")
  override fun register(@RequestBody user: UserCreateRequest): ResponseEntity<Unit> {
    iUserService.createUser(user)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @GetMapping("users")
  override fun users(): ResponseEntity<UserListResponse> =
    ResponseEntity.ok(iUserService.getUsers())

}