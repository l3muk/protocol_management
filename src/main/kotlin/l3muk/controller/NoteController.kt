package l3muk.controller

import l3muk.dto.NoteCreateRequest
import l3muk.dto.NoteResponse
import l3muk.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

interface NoteApi {
  fun createNote(note: NoteCreateRequest): ResponseEntity<Unit>
  fun getNoteByProtocol(protocol: Int): ResponseEntity<NoteResponse>
}

@RestController
@RequestMapping("/api/notes")
class NoteController(
  private val noteService: NoteService
) : NoteApi {

  @PostMapping
  override fun createNote(@RequestBody note: NoteCreateRequest): ResponseEntity<Unit> {
    val noteId = noteService.createNote(note)
    val location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(noteId)
      .toUri()

    return ResponseEntity.created(location).build()
  }

  @GetMapping
  override fun getNoteByProtocol(@RequestParam protocol: Int) =
    ResponseEntity.ok(noteService.getNoteByProtocol(protocol))




}