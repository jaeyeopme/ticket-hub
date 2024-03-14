package me.jaeyeop.tickethub.support.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import me.jaeyeop.tickethub.support.error.ErrorCode
import org.springframework.http.ResponseEntity

@JsonPropertyOrder("code", "message", "data")
data class ErrorResult(
    val code: ErrorCode,
    val data: Any?
) {

    val message: String = code.message

    companion object {
        fun of(
            errorCode: ErrorCode,
            data: Any?
        ): ResponseEntity<ErrorResult> {
            val body = ErrorResult(errorCode, data)

            return ResponseEntity(body, errorCode.httpStatus)
        }
    }

}
