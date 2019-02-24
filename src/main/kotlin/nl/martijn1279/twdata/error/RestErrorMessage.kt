package nl.martijn1279.twdata.error

import java.sql.Timestamp

data class RestErrorMessage(val errorMessages: List<String?>,
                            val errorCode: ErrorCode,
                            val timestamp: Timestamp = Timestamp(System.currentTimeMillis()))

