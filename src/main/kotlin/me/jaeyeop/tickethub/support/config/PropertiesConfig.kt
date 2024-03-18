package me.jaeyeop.tickethub.support.config

import me.jaeyeop.tickethub.support.properties.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(JwtProperties::class)
@Configuration
class PropertiesConfig
