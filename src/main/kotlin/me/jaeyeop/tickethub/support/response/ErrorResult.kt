package me.jaeyeop.tickethub.support.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import me.jaeyeop.tickethub.support.error.ErrorCode
import org.springframework.http.ResponseEntity

@JsonPropertyOrder("code", "message", "data")
data class ErrorResult<T>(
    val code: ErrorCode,
    val data: T? = null,
) {
    val message = code.message

    companion object {
        fun from(errorCode: ErrorCode): ResponseEntity<ErrorResult<Unit>> {
            val body = ErrorResult<Unit>(errorCode)

            return ResponseEntity(body, errorCode.httpStatus)
        }

        fun <T> of(
            errorCode: ErrorCode,
            data: T?,
        ): ResponseEntity<ErrorResult<T>> {
            val body =
                ErrorResult(
                    code = errorCode,
                    data = data,
                )

            return ResponseEntity(body, errorCode.httpStatus)
        }
    }
}
