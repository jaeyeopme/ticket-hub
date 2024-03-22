package me.jaeyeop.tickethub.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {
    NOT_FOUND_IDENTITY(HttpStatus.NOT_FOUND, "존재하지 않는 식별자입니다.", LogLevel.ERROR),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.", LogLevel.ERROR),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값이 존재합니다.", LogLevel.ERROR),

    INVALID_EMAIL_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 이메일 또는 비밀번호입니다.", LogLevel.ERROR),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.", LogLevel.ERROR),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.", LogLevel.ERROR),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", LogLevel.ERROR),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", LogLevel.ERROR),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.", LogLevel.ERROR),
    DUPLICATED_MEMBER_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.", LogLevel.ERROR),
}
