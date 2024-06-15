package presentation.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import data.model.entity.PetEntity
import data.repository.PetsRepoitoryImpl
import domain.model.CalendarActivity
import domain.model.enums.PetCalendarActivityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetCalendarActivityViewModel: ViewModel(), KoinComponent {
    private val _pets = MutableStateFlow<ArrayList<PetEntity>>(arrayListOf())
    val pets: StateFlow<ArrayList<PetEntity>> = _pets
    val petRepository: PetsRepoitoryImpl by inject()

    private val _state = MutableStateFlow(PetCalendarActivityState.IDLE)
    val state: StateFlow<PetCalendarActivityState> = _state

    fun loadPets(){
        viewModelScope.launch {
            petRepository.fetch().collect {
                pets.value.clear()
                pets.value.addAll(it)
            }
        }
    }

    fun createActivity(calendarActivity: CalendarActivity) {
        viewModelScope.launch {
            petRepository.createCalendarActivity(calendarActivity).collect {
                when(it){
                    is Resources.Error -> _state.value = PetCalendarActivityState.ERROR
                    is Resources.Loading -> _state.value = PetCalendarActivityState.LOADING
                    is Resources.Success -> {
                        _state.value = PetCalendarActivityState.IDLE
                    }
                }
            }
        }
    }
}