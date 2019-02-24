package nl.martijn1279.twdata.error

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ServiceExceptionMapper {

    @ExceptionHandler(ServiceException::class)
    fun toResponse(exception: ServiceException): ResponseEntity<RestErrorMessage> {
        return ResponseEntity
                .status(exception.errorCode.statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestErrorMessage(listOf(exception.errorCode.description), exception.errorCode))
    }
}
