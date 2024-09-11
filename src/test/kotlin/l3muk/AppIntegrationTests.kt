package l3muk

import l3muk.controller.ErrorResponseBody
import l3muk.dto.NoteCreateRequest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
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
    val response = restTemplate.getForEntity<ErrorResponseBody>(
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

    val response = restTemplate.postForEntity<Unit>(
      "/api/notes",
      note
    )

    assertEquals(HttpStatus.CREATED, response.statusCode)
    assertTrue(response.headers.location.toString().contains("/api/notes/$noteId"))
  }
}