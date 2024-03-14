package me.jaeyeop.tickethub.support.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResult<T>(
    val data: T?,
) {

    companion object {
        fun created(): ResponseEntity<Unit> {
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }

        fun <T> ok(data: T): ResponseEntity<ApiResult<T>> {
            val body = ApiResult(data)

            return ResponseEntity(body, HttpStatus.OK)
        }
    }

}
