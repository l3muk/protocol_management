package l3muk.service

import l3muk.dto.NoteCreateRequest
import l3muk.dto.NoteResponse
import l3muk.exception.DuplicateProtocolException
import l3muk.exception.NoteNotFoundException
import l3muk.model.Note
import l3muk.repository.NoteRepository
import org.springframework.stereotype.Service

interface NoteService {
  fun createNote(note: NoteCreateRequest): Long?
  fun getNoteByProtocol(protocol: Int): NoteResponse?
}

@Service
class NoteServiceImpl(
  private val noteRepository: NoteRepository
) : NoteService {

  override fun createNote(note: NoteCreateRequest): Long? {
    noteRepository.findByProtocol(note.protocol)?.let {
      throw DuplicateProtocolException("the protocol [${note.protocol}] already registered")
    }

    return noteRepository.save(note.toModel()).id
  }

  override fun getNoteByProtocol(protocol: Int) =
    noteRepository.findByProtocol(protocol)
      ?.toResponse()
      ?: throw NoteNotFoundException("note not found")

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

