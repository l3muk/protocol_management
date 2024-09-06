package l3muk.service

import l3muk.dto.NoteCreateRequest
import l3muk.dto.NoteResponse
import l3muk.model.Note
import l3muk.repository.NoteRepository
import org.springframework.stereotype.Service

interface NoteService {
  fun createNote(note: NoteCreateRequest)
  fun getNoteByProtocol(protocol: Int): NoteResponse?
}

@Service
class NoteServiceImpl(
  private val noteRepository: NoteRepository
) : NoteService {

  override fun createNote(note: NoteCreateRequest) {
    noteRepository.findByProtocol(note.protocol)?.let {
      throw RuntimeException("that protocol already registered")
    }
    noteRepository.save(note.toModel())
  }

  override fun getNoteByProtocol(protocol: Int): NoteResponse? {
    return noteRepository.findByProtocol(protocol)
      ?.toResponse()
  }

  private fun NoteCreateRequest.toModel() = Note(
    protocol = protocol,
    docType = docType,
    docName = docName,
    recipient = recipient,
    author = author
  )

  private fun Note.toResponse() = NoteResponse(
    id = id!!,
    protocol = protocol,
    docType = docType,
    docName = docName,
    recipient = recipient,
    author = author
  )
}