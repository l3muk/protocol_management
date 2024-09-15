package l3muk.controller

import l3muk.exception.DuplicateProtocolException
import l3muk.exception.DuplicateUsernameException
import l3muk.exception.NoteNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ResponseErrorHandler {

    @ExceptionHandler
    fun handleNoteNotFoundException(ex: NoteNotFoundException): ResponseEntity<ErrorResponseBody> {
        val body = ErrorResponseBody(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleDuplicateProtocolException(ex: DuplicateProtocolException): ResponseEntity<ErrorResponseBody> {
        val body = ErrorResponseBody(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleDuplicateUsernameException(ex: DuplicateUsernameException): ResponseEntity<ErrorResponseBody> {
        val body = ErrorResponseBody(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}

data class ErrorResponseBody(
    val status: Int? = null,
    val message: String? = null
)