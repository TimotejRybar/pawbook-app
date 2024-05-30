package presentation.screen.calendar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import core.util.YearMonth
import data.model.entity.CalendarActivityEntity
import data.model.entity.PetEntity
import data.repository.PetCalendarRepositoryImpl
import domain.model.enums.LoginState
import domain.model.enums.MyPetsState
import domain.model.enums.PetCalendarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.calendar.CalendarUiState
import utils.compose.CalendarDataSource
import utils.compose.CalendarUtils

class PetCalendarViewModel: ViewModel(), KoinComponent {

    private val _pets = MutableStateFlow<List<PetEntity>>(arrayListOf())
    val pets: StateFlow<List<PetEntity>> = _pets

    private val _state = MutableStateFlow(PetCalendarState.IDLE)
    val state: StateFlow<PetCalendarState> = _state

    private val _calendarActivities =
        MutableStateFlow<List<CalendarActivityEntity>>(arrayListOf())
    val calendarActivities: StateFlow<List<CalendarActivityEntity>> = _calendarActivities
    private val petCalendarRepository: PetCalendarRepositoryImpl by inject()

//    val _uiState =
    //      MutableStateFlow(CalendarUiState(yearMonth.value, dataSource.getDates(yearMonth.value)))
    // val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    fun loadCalendarActivities() {
        viewModelScope.launch {
            petCalendarRepository.fetch().collect { it2 ->
                _calendarActivities.value = it2
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

    init {

        val dataSource = CalendarDataSource()
        var yearMonth = mutableStateOf(
            YearMonth(
                CalendarUtils.getCurrentYear(), Month(
                    CalendarUtils.getCurrentMonth()
                )
            )
        )
    }
}