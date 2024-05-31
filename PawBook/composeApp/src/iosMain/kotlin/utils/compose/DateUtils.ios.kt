package utils.compose

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun LocalDateTime.format(): String {
    val date = this.toNSDateComponents()
    val formatter = NSDateFormatter().apply {
        dateFormat = "dd.MM.yyyy"
        locale = NSLocale.currentLocale
    }
    return formatter.stringFromDate(date.date as NSDate)
}