package presentation.screen.calendar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.YearMonth
import data.model.entity.CalendarActivityEntity
import data.model.entity.PetEntity
import data.repository.PetCalendarRepositoryImpl
import domain.model.enums.PetCalendarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.compose.CalendarUtils

class PetCalendarViewModel: ViewModel(), KoinComponent {

    private val _pets = MutableStateFlow<List<PetEntity>>(arrayListOf())
    val pets: StateFlow<List<PetEntity>> = _pets

    private val _state = MutableStateFlow(PetCalendarState.IDLE)
    val state: StateFlow<PetCalendarState> = _state

    private val _calendarActivities = MutableStateFlow<List<CalendarActivityEntity>>(arrayListOf())
    val calendarActivities: StateFlow<List<CalendarActivityEntity>> = _calendarActivities
    private val petCalendarRepository: PetCalendarRepositoryImpl by inject()

    fun loadCalendarActivities() {
        viewModelScope.launch {
            petCalendarRepository.fetch().collect {
                _calendarActivities.value = it
            }
        }
    }

    fun loadPets() {
        viewModelScope.launch {
            petCalendarRepository.fetchPets().collect{ it ->
                _pets.value = it
            }
        }
    }

    fun getCalendarActivity(dayDate: LocalDate, activities: List<CalendarActivityEntity>): CalendarActivityEntity? {
        return activities.find {
            it.start.date == dayDate
        }
    }

    fun getCurrentYearMonthLocalized(): String {
        var yearMonth = mutableStateOf(
            YearMonth(
                CalendarUtils.getCurrentYear(), Month(
                    CalendarUtils.getCurrentMonth()
                )
            )
        )

        val dateFormat = LocalDate.Format {
            monthName(names = MonthNames.ENGLISH_FULL)
            char(' ')
            year()
        }

        return LocalDate(yearMonth.value.year, yearMonth.value.month, 1).format(dateFormat)
    }
}