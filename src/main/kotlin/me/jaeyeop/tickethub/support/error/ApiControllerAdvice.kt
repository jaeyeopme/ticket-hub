package me.jaeyeop.tickethub.support.error

import me.jaeyeop.tickethub.support.response.ErrorResult
import me.jaeyeop.tickethub.support.response.FieldData
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ApiException::class)
    fun handelApiException(e: ApiException)
            : ResponseEntity<ErrorResult<Any?>> {
        val errorCode = e.errorCode

        logging(
            errorCode = errorCode,
            throwable = e
        )

        return ErrorResult.of(e.errorCode, e.data)
    }

    /**
     * 인증 예외 처리
     *
     * @param e Authentication 예외
     * @return Http 401 UNAUTHORIZED
     */
    @ExceptionHandler(AuthenticationException::class)
    fun authenticationExceptionHandler(e: AuthenticationException)
            : ResponseEntity<ErrorResult<Unit>> {
        logging(
            errorCode = ErrorCode.UNAUTHORIZED,
            throwable = e
        )

        return ErrorResult.from(ErrorCode.UNAUTHORIZED)
    }

    /**
     * 인가 예외 처리
     *
     * @param e Authorize 예외
     * @return Http 403 FORBIDDEN
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedExceptionHandler(e: AccessDeniedException)
            : ResponseEntity<ErrorResult<Unit>> {
        logging(
            errorCode = ErrorCode.FORBIDDEN,
            throwable = e
        )

        return ErrorResult.from(ErrorCode.FORBIDDEN)
    }

    /**
     * 필드 검증 예외 처리
     *
     * @param e Validation 예외
     * @return 에러 필드를 포함한 Http 400 BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException)
            : ResponseEntity<ErrorResult<List<FieldData>>> {
        logging(
            errorCode = ErrorCode.INVALID_INPUT_VALUE,
            throwable = e
        )

        val data = e.bindingResult.fieldErrors.map { FieldData.of(it) }

        return ErrorResult.of(ErrorCode.INVALID_INPUT_VALUE, data)
    }

    /**
     * 예상하지 못한 예외 처리
     *
     * @param e 예상하지 못한 예외
     * @return Http 500 INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception)
            : ResponseEntity<ErrorResult<Unit>> {
        logging(
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR,
            throwable = e,
        )

        return ErrorResult.from(ErrorCode.INTERNAL_SERVER_ERROR)
    }

    private fun logging(
        errorCode: ErrorCode,
        throwable: Throwable
    ) {
        when (errorCode.logLevel) {
            LogLevel.ERROR -> logger.error(
                "${throwable.javaClass.simpleName} : ${throwable.message}",
                throwable
            )

            LogLevel.WARN -> logger.warn(
                "${throwable.javaClass.simpleName} : ${throwable.message}",
                throwable
            )

            else -> logger.info(
                "${throwable.javaClass.simpleName} : ${throwable.message}",
                throwable
            )
        }
    }

}