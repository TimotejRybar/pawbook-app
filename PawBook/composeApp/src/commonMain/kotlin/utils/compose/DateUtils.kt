package utils.compose

import io.ktor.util.date.GMTDate
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

expect fun LocalDateTime.format(): String

fun GMTDate.toInstant(): Instant = Instant.fromEpochMilliseconds(this.timestamp)

