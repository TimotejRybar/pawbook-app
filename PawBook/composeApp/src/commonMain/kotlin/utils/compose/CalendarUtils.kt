package utils.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import core.util.YearMonth
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.friday
import pawbook.composeapp.generated.resources.monday
import pawbook.composeapp.generated.resources.saturday
import pawbook.composeapp.generated.resources.sunday
import pawbook.composeapp.generated.resources.thursday
import pawbook.composeapp.generated.resources.tuesday
import pawbook.composeapp.generated.resources.wednesday
import presentation.components.calendar.CalendarUiState
import utils.compose.CalendarUtils.getDayOfMonthStartingFromMonday
import kotlin.time.ExperimentalTime

@Composable
fun DayOfWeek.getDisplayName(): String? {
    return getWeekDayShortName(this, Locale.current)
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun getWeekDayShortName( day: DayOfWeek, locale: Locale): String? {
    return when(day) {
        DayOfWeek.MONDAY -> stringResource(Res.string.monday)
        DayOfWeek.TUESDAY -> stringResource(Res.string.tuesday)
        DayOfWeek.WEDNESDAY -> stringResource(Res.string.wednesday)
        DayOfWeek.THURSDAY -> stringResource(Res.string.thursday)
        DayOfWeek.FRIDAY -> stringResource(Res.string.friday)
        DayOfWeek.SATURDAY -> stringResource(Res.string.saturday)
        DayOfWeek.SUNDAY -> stringResource(Res.string.sunday)
        else -> throw IllegalStateException("Unsupported DayOfWeek: ${day.name}")
    }
}

object CalendarUtils {
    @OptIn(ExperimentalTime::class)
    fun YearMonth.getDayOfMonthStartingFromMonday(): List<LocalDate> {
        val firstDayOfMonth = LocalDate(year, this.month.number, 1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek
        val daysToAdd = if (firstDayOfWeek == DayOfWeek.MONDAY) 0 else (DayOfWeek.MONDAY.ordinal - firstDayOfWeek.ordinal + 7) % 7
        val firstMondayOfMonth = firstDayOfMonth.plus(daysToAdd, DateTimeUnit.DAY)
        val firstDayOfNextMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH)

        return generateSequence(firstMondayOfMonth) { it.plus(1, DateTimeUnit.DAY) }
            .takeWhile { it < firstDayOfNextMonth }
            .toList()
    }

    fun getCurrentYear(): Int {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return currentDateTime.year
    }

    fun getCurrentMonth(): Int {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return currentDateTime.monthNumber
    }

}



class CalendarDataSource {

    fun getDates(yearMonth: YearMonth): List<CalendarUiState.Date> {
        val now = Clock.System.now()
        val tz = TimeZone.currentSystemDefault()
        val today = now.toLocalDateTime(tz).date

        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                CalendarUiState.Date(
                    dayOfMonth = if (date.month.ordinal == yearMonth.month.ordinal) {
                        "${date.dayOfMonth}"
                    } else {
                        "" // Fill with empty string for days outside the current month
                    },

                    isSelected = date == today && date.month.ordinal == yearMonth.month.ordinal
                )
            }
    }
}
