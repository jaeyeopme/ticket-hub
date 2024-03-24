package me.jaeyeop.tickethub.support.config.time

import java.util.Date

interface DateTimeProvider {
    fun nowDate(): Date
}
