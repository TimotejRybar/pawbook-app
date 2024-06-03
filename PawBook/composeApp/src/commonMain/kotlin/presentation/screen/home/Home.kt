package presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.util.YearMonth
import kotlinx.datetime.Month
import org.koin.compose.koinInject
import presentation.screen.calendar.PetCalendarContent
import presentation.screen.calendar.PetCalendarOverview
import utils.compose.CalendarDataSource
import utils.compose.CalendarUtils

@Composable
fun Home(viewModel: HomeViewModel = koinInject() ) {
    /*
    val activities by viewModel.calendarActivities.collectAsState()
    val dataSource = remember { CalendarDataSource() }
    val yearMonth = remember { mutableStateOf(
        YearMonth(
        CalendarUtils.getCurrentYear(), Month(CalendarUtils.getCurrentMonth())
        )
    ) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row {
            Column {
                PetCalendarContent(dataSource.getDates(yearMonth.value)) {}
                PetCalendarOverview(activities)
            }
        }
    }

    LaunchedEffect(true){
        viewModel.loadCalendarActivities()
    }*/
}