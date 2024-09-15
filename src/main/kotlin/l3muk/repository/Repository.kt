package l3muk.repository

import l3muk.model.Note
import l3muk.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {
  fun findByProtocol(protocol: Int): Note?
}

interface UserRepository : JpaRepository<User, Long> {
  fun findByUsername(username: String): User?
  fun existsByUsername(username: String): Boolean
}