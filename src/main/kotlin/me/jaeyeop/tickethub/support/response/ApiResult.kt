package me.jaeyeop.tickethub.support.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonPropertyOrder("data")
data class ApiResult<T>(
    val data: T?,
) {
    companion object {
        fun created(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.CREATED).build()

        fun ok(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.OK).build()

        fun <T> ok(data: T): ResponseEntity<ApiResult<T>> {
            val body = ApiResult(data)

            return ResponseEntity(body, HttpStatus.OK)
        }
    }
}
