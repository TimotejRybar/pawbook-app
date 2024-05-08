package presentation.components.calendar

import core.util.YearMonth

data class CalendarUiState(
    val yearMonth: YearMonth,
    val dates: List<Date>
) {
    data class Date(
        val dayOfMonth: String,
        val isSelected: Boolean
    )

    companion object {
        fun empty() : Date {
            return Date("", false)
        }
    }
}