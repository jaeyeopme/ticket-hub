package me.jaeyeop.tickethub.support.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import me.jaeyeop.tickethub.support.error.ErrorCode
import org.springframework.http.ResponseEntity

@JsonPropertyOrder("code", "message", "data")
data class ErrorResult<T>(
    val code: ErrorCode,
    val data: T?
) {

    val message: String = code.message

    companion object {
        fun <T> of(
            errorCode: ErrorCode,
            data: T?
        ): ResponseEntity<ErrorResult<T>> {
            val body = ErrorResult(errorCode, data)

            return ResponseEntity(body, errorCode.httpStatus)
        }
    }

}
