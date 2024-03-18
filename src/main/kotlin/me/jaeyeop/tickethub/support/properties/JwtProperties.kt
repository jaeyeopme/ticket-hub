package me.jaeyeop.tickethub.support.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = false)
data class JwtProperties(
    val accessKey: String,
    val accessExp: Duration,
    val refreshKey: String,
    val refreshExp: Duration,
)
