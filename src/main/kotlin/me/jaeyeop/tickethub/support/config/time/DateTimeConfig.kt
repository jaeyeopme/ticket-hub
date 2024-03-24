package me.jaeyeop.tickethub.support.config.time

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Date

@Configuration
class DateTimeConfig {
    @Bean
    fun dateTimeProvider(): DateTimeProvider = object : DateTimeProvider {
        override fun nowDate(): Date = Date()
    }
}
