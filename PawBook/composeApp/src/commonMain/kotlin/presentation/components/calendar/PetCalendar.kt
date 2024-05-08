package presentation.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import presentation.theme.colors.LocalAppColors
import utils.compose.CalendarDataSource
import utils.compose.CalendarUtils
import core.util.YearMonth
import utils.compose.getDisplayName

@Composable
fun PetCalendar() {

    val dataSource = remember { CalendarDataSource() }
    var yearMonth = remember { mutableStateOf(YearMonth(
        CalendarUtils.getCurrentYear(), Month(
            CalendarUtils.getCurrentMonth()))) }
    val _uiState = MutableStateFlow(CalendarUiState(yearMonth.value, dataSource.getDates(yearMonth.value)))
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    PetCalendarContent(dataSource.getDates(yearMonth.value)) {
    }
}

@Composable
fun PetCalendarContent(
    dates: List<CalendarUiState.Date>,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column {
        var index = 0
        Row {

            for(i in 1..daysOfWeek.size) {
                PetCalendarWeekDay(daysOfWeek[i-1], modifier = Modifier.weight(1f))
            }
        }
        repeat(6) {
            if (index >= dates.size) return@repeat
            Row {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.empty()
                    PetCalendarItem(
                        date = item,
                        onClickListener = onDateClickListener,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
}

@Composable
fun PetCalendarWeekDay(s: String, modifier: Modifier) {
    Box( modifier = modifier
        .background(
            color = LocalAppColors.current.primary
        )
        .border(1.dp, Color.Black)
    ) {
        Text(
            text = s,
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(0.dp, 0.dp, 4.dp, 0.dp)
        )
    }
}

@Composable
fun PetCalendarItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (true) {
                    LocalAppColors.current.secondary
                } else {
                    Color.Transparent
                }
            )
            .border(1.dp, Color.Black)
            .clickable {
                onClickListener(date)
            }
    ) {
        Text(
            text = date.dayOfMonth,
            fontSize = 10.sp,
            color = LocalAppColors.current.primary,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(0.dp, 0.dp, 4.dp, 0.dp)
        )
    }
}


val daysOfWeek: Array<String>
    @Composable
    get() {
        val daysOfWeek = Array(7) { "" }

        for (dayOfWeek in DayOfWeek.entries) {
            val localizedDayName = dayOfWeek.getDisplayName()
            daysOfWeek[dayOfWeek.ordinal] = localizedDayName as String
        }

        return daysOfWeek
    }