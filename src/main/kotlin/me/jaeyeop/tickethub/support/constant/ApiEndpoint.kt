package me.jaeyeop.tickethub.support.constant

object ApiEndpoint {
    private const val V1_API_PATH = "/api/v1"

    /**
     * URL
     */
    const val MEMBER = "$V1_API_PATH/members"
    const val AUTH = "$V1_API_PATH/auth"

    /**
     * ENDPOINT
     */
    const val LOGIN_ENDPOINT = "/login"
    const val LOGOUT_ENDPOINT = "/logout"
    const val REFRESH_ACCESS_TOKEN_ENDPOINT = "/refresh"
}


