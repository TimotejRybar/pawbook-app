package utils.compose

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

actual fun LocalDateTime.format(): String {
     return DateTimeFormatter
        .ofPattern("dd.MM.yyyy", Locale.getDefault())
        .format(this.toJavaLocalDateTime())
}