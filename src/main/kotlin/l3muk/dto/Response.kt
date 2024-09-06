package l3muk.dto

data class NoteResponse(
  val id: Long,
  val protocol: Int,
  val docType: String,
  val docName: String,
  val recipient: String,
  val author: String
)


