package l3muk.exception

class NoteNotFoundException(message: String) : RuntimeException(message)

class DuplicateProtocolException(message: String) : RuntimeException(message)

class DuplicateUsernameException(message: String) : RuntimeException(message)