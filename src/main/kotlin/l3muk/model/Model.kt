package l3muk.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "notes")
data class Note(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  @Column(unique = true)
  val protocol: Int,
  val docType: String,
  val docName: String,
  val recipient: String,
  val author: String
)

