package me.jaeyeop.tickethub.auth.adaptor.`in`

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.LoginRequest
import me.jaeyeop.tickethub.auth.application.port.`in`.AuthQueryUseCase
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.support.constant.ApiEndpoint
import me.jaeyeop.tickethub.support.response.ApiResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(ApiEndpoint.AUTH)
@RestController
class AuthWebAdaptor(
    private val authQueryUseCase: AuthQueryUseCase,
    private val authCommandUseCase: AuthCommandUseCase,
) {

    @PostMapping(ApiEndpoint.LOGIN_ENDPOINT)
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResult<TokenPair>> {
        val tokenPayload = authQueryUseCase.login(request)
        val tokenPair = authCommandUseCase.updateRefreshToken(tokenPayload)

        return ApiResult.ok(tokenPair)
    }

}
