package nl.martijn1279.twdata.error

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ConstraintViolationExceptionMapper {

    companion object {
        private val log = LoggerFactory.getLogger(ConstraintViolationException::class.java)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun toResponse(exception: MethodArgumentNotValidException): ResponseEntity<RestErrorMessage> {
        val messages = exception.bindingResult.allErrors.map(ObjectError::getDefaultMessage)
        log.warn("Fout opgetreden: {}", messages)
        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestErrorMessage(messages, ErrorCode.VALIDATION_ERROR))
    }
}