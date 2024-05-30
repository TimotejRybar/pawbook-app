package domain.model.result

import domain.model.CalendarActivity
import kotlinx.serialization.Serializable

@Serializable
data class  FetchCalendarForMonthResult(
    val calendar: List<CalendarActivity> = listOf()
)
