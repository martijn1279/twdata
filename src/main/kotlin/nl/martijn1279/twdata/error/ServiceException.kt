package nl.martijn1279.twdata.error

class ServiceException @JvmOverloads constructor(errorCode: ErrorCode, cause: Throwable? = null) : Exception("Fout opgetreden: $errorCode", cause) {

    val errorCode: ErrorCode

    init {
        this.errorCode = nullSafeFoutcode(errorCode)
    }

    private fun nullSafeFoutcode(errorCode: ErrorCode?): ErrorCode {
        return errorCode ?: ErrorCode.UNKNOWN_ERROR
    }
}