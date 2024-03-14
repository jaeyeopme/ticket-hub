package me.jaeyeop.tickethub.support.error

class ApiException(
    val errorCode: ErrorCode,
    val data: Any? = null,
) : RuntimeException(errorCode.message)