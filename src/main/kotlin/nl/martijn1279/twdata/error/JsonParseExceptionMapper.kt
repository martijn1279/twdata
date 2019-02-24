package nl.martijn1279.twdata.error

import com.fasterxml.jackson.core.JsonParseException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class JsonParseExceptionMapper {

    companion object {
        private val log = LoggerFactory.getLogger(JsonParseExceptionMapper::class.java)
    }

    @ExceptionHandler(JsonParseException::class)
    fun toResponse(exception: JsonParseException): ResponseEntity<RestErrorMessage> =
            ResponseEntity
                    .status(ErrorCode.INVALID_FORMAT.statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(RestErrorMessage(listOf(ErrorCode.INVALID_FORMAT.description), ErrorCode.INVALID_FORMAT))
                    .also { log.warn("Error occured: ${exception.message}") }
}