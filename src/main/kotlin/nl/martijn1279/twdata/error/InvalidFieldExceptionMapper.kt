package nl.martijn1279.twdata.error

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class InvalidFieldExceptionMapper {

    companion object {
        private val log = LoggerFactory.getLogger(InvalidFieldExceptionMapper::class.java)
    }

    @ExceptionHandler(InvalidFormatException::class, MethodArgumentTypeMismatchException::class)
    fun toResponse(exception: Exception): ResponseEntity<RestErrorMessage> =
            ResponseEntity
                    .status(ErrorCode.INVALID_FIELD.statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(RestErrorMessage(listOf(ErrorCode.INVALID_FIELD.description), ErrorCode.INVALID_FIELD))
                    .also { log.warn("Fout opgetreden: ${exception.message}") }
}