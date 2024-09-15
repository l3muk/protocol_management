package l3muk

import l3muk.controller.ErrorResponseBody
import l3muk.dto.NoteCreateRequest
import l3muk.dto.UserCreateRequest
import l3muk.dto.UserListResponse
import l3muk.dto.UserResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import kotlin.math.exp
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AppIntegrationTests(
  @Autowired val restTemplate: TestRestTemplate,
  @LocalServerPort val port: Int) {

  @Test
  fun `should return not found`() {
    val nonExistentProtocol = 9999999
    val response = restTemplate.withBasicAuth("admin", "123")
      .getForEntity<ErrorResponseBody>(
      "/api/notes?protocol=${nonExistentProtocol}")

    assertNotNull(response)
    assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    assertEquals("note not found", response.body?.message)
  }

  @Test
  fun `should create a note and return location header`() {
    val note = NoteCreateRequest(
      protocol = 1020300,
      docType = "Memorando",
      docName = "Operação Círio Curuçá",
      recipient = "CPR III",
      author = "Lemuel"
    )

    val noteId = 1L

    val response = restTemplate.withBasicAuth("admin", "123")
      .postForEntity<Unit>(
      "/api/notes",
      note
    )

    assertEquals(HttpStatus.CREATED, response.statusCode)
    assertTrue(response.headers.location.toString().contains("/api/notes/$noteId"))
  }

  @Test
  fun `should register a new user and return created`() {
    val user = UserCreateRequest(
      username = "lemuel",
      password = "123"
    )

    val response = restTemplate.postForEntity<Unit>(
      "/api/auth/register",
      user
    )

    assertEquals(HttpStatus.CREATED, response.statusCode)
  }

  @Test
  fun `should return all users`() {
        val response = restTemplate.getForEntity<UserListResponse>(
      "/api/auth/users"
    )

    assertNotNull(response)
    assertEquals(HttpStatus.OK, response.statusCode)
    assertTrue(response.hasBody())

    val body = response.body!!

    val expectedUser = UserResponse(
      1L,
      "admin",
      "\$2a\$12\$oQ7AwZF3zrBIfdLbvlxhwOV5ZVkQZHh8HjodHWgxm/2YslH6e518O"
    )

    assertEquals(1, body.total )
    assertContains(body.users, expectedUser)
  }
}