package me.jaeyeop.tickethub.support.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    private val body: T? = null,
    private val status: HttpStatus,
) : ResponseEntity<T>(body, status) {

    companion object {
        fun <T> created(): ApiResponse<T> {
            return ApiResponse(status = HttpStatus.CREATED);
        }
    }

}
