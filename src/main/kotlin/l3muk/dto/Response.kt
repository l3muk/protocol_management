package l3muk.dto

data class NoteResponse(
  val id: Long,
  val protocol: Int,
  val docType: String,
  val docName: String,
  val recipient: String,
  val author: String
)

data class UserResponse(
  val id: Long,
  val username: String,
  val password: String
)

data class UserListResponse(
  val users: List<UserResponse>,
  val total: Int
)

