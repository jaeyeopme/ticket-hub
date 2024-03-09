package me.jaeyeop.tickethub.support.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResult<T>(
    private val body: T? = null,
    private val status: HttpStatus,
) : ResponseEntity<T>(body, status) {

    companion object {
        fun <T> created(): ApiResult<T> {
            return ApiResult(status = HttpStatus.CREATED);
        }

        fun <T> ok(body: T): ApiResult<T> {
            return ApiResult(status = HttpStatus.OK, body = body);
        }
    }

}
