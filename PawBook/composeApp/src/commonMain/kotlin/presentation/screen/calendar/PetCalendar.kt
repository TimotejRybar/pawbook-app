package presentation.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cut
import core.util.YearMonth
import data.model.entity.CalendarActivityEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject
import presentation.components.calendar.CalendarUiState
import presentation.theme.colors.LocalAppColors
import utils.compose.CalendarDataSource
import utils.compose.CalendarUtils
import utils.compose.getDisplayName

@Composable
fun PetCalendar(viewModel: PetCalendarViewModel = koinInject()) {
    val activities by viewModel.calendarActivities.collectAsState()
    val dataSource = remember { CalendarDataSource() }
    val yearMonth = remember { mutableStateOf(YearMonth(
        CalendarUtils.getCurrentYear(), Month(CalendarUtils.getCurrentMonth()))) }

    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(32.dp)) {
        Column {
            PetCalendarContent(viewModel, activities, dataSource.getDates(yearMonth.value)) {}
            PetCalendarOverview(activities)
        }
    }

    LaunchedEffect(true){
        viewModel.loadCalendarActivities()
    }
}

@Composable
fun PetCalendarOverviewItem(calendarActivity: CalendarActivityEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalAppColors.current.primary,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = calendarActivity.activityType.value,
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
            Text(
                text = formatDate(calendarActivity.start),
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}


fun formatDate(date: LocalDateTime): String{
    return (date.date.dayOfMonth.toString() + "." + date.date.monthNumber)
}

@Composable
fun PetCalendarOverview(items: List<CalendarActivityEntity>) {
    LazyColumn(
        modifier = Modifier
            .heightIn(max = 150.dp)
    ) {
        items(items) { item ->
            PetCalendarOverviewItem(item)
        }

    }
}

@Composable
fun PetCalendarContent(
    viewModel: PetCalendarViewModel,
    activities: List<CalendarActivityEntity>,
    dates: List<CalendarUiState.Date>,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    val currentMoment = Clock.System.now()
    val currentDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
    val currentYear = currentDateTime.year
    val currentMonth = currentDateTime.month

    Column {
        var index = 0
        Row {
            for (i in 1..daysOfWeek.size) {
                PetCalendarWeekDay(daysOfWeek[i - 1], modifier = Modifier.weight(1f))
            }
        }
        repeat(6) {
            if (index >= dates.size) return@repeat
            Row {
                repeat(7) { day ->
                    if (index < dates.size) {
                        val date = dates[index]
                        val dateTime = LocalDateTime(currentYear, currentMonth, date.dayOfMonth.toInt(), 12, 0)
                        val activity = viewModel.getCalendarActivity(dateTime.date, activities)
                        PetCalendarItem(
                            activity = activity,
                            date = dateTime,
                            onClickListener = { onDateClickListener(date) },
                            modifier = Modifier.weight(1f).heightIn(48.dp, 128.dp)
                        )
                        index++
                    }
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
        .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center
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
    date: LocalDateTime,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier,
    activity: CalendarActivityEntity?
) {
    Box(
        modifier = modifier
            .background(
                color = if (true) {
                    LocalAppColors.current.darkGray
                } else {
                    Color.Transparent
                }
            )
            .border(1.dp, Color.Black)
            .clickable {
            }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(4.dp).align(Alignment.Center)) {
            if(activity != null) {
                Icon(
                    FontAwesomeIcons.Solid.Cut,
                    "",
                    tint = LocalAppColors.current.primary,
                    modifier = Modifier.align(Alignment.Center).size(16.dp)
                )
            }
        }
        Text(
            text = date.dayOfMonth.toString(),
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