package l3muk.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

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

@Entity(name = "users")
data class User(
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  val id: Long? = null,
  @Column(unique = true)
  val username: String,
  val hashedPassword: String,
)