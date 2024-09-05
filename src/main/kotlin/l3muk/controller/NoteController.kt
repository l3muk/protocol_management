package l3muk.controller

import l3muk.dto.NoteCreateRequest
import l3muk.dto.NoteResponse
import l3muk.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

interface NoteApi {
  fun createNote(note: NoteCreateRequest)
  fun getNoteByProtocol(protocol: Int): NoteResponse?
}

@RestController
@RequestMapping("/api/notes")
class NoteController(
  private val noteService: NoteService
) : NoteApi {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  override fun createNote(@RequestBody note: NoteCreateRequest) = noteService.createNote(note)

  @GetMapping
  override fun getNoteByProtocol(@RequestBody protocol: Int) = noteService.getNoteByProtocol(protocol)
}