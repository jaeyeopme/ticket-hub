package me.jaeyeop.tickethub.support.error

import me.jaeyeop.tickethub.support.response.ErrorResult
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ApiException::class)
    fun handelApiException(e: ApiException): ResponseEntity<ErrorResult> {
        val errorCode = e.errorCode

        when (errorCode.logLevel) {
            LogLevel.ERROR -> log.error("ApiException : {}", e.message, e)
            LogLevel.WARN -> log.warn("ApiException : {}", e.message, e)
            else -> log.info("CoreApiException : {}", e.message, e)
        }

        return ErrorResult.of(e.errorCode, e.data)
    }

}