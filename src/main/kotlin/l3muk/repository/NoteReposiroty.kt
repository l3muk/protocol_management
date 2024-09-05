package l3muk.repository

import l3muk.model.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {
  fun findByProtocol(protocol: Int): Note?
}