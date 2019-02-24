package nl.martijn1279.twdata.error

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MissingFieldExceptionMapper {

    companion object {
        private val log = LoggerFactory.getLogger(MissingFieldExceptionMapper::class.java)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun toResponse(exception: HttpMessageNotReadableException): ResponseEntity<RestErrorMessage> {
        val cause = exception.cause
        val message = when (cause) {
            is MissingKotlinParameterException -> "'${cause.path[0].fieldName}' is verplicht."
            else -> "Kan het vraagbericht niet lezen."
        }
        log.warn("Fout opgetreden: {}", message)
        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestErrorMessage(listOf(message), ErrorCode.VALIDATION_ERROR))
    }
}
