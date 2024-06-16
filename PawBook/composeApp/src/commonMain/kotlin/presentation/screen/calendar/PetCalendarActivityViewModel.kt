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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetCalendarActivityViewModel: ViewModel(), KoinComponent {
    private val _pets = MutableStateFlow<ArrayList<PetEntity>>(arrayListOf())
    val pets: StateFlow<ArrayList<PetEntity>> = _pets
    val petRepository: PetsRepoitoryImpl by inject()

    private val _petProfilePhotos = MutableStateFlow<MutableMap<String, ByteArray>>(mutableMapOf())
    val petProfilePhotos: StateFlow<Map<String, ByteArray>> = _petProfilePhotos

    private val _requestPet = MutableStateFlow<PetEntity?>(null)
    val requestPet: StateFlow<PetEntity?> = _requestPet

    private val _state = MutableStateFlow(PetCalendarActivityState.IDLE)
    val state: StateFlow<PetCalendarActivityState> = _state

    fun loadPets(requestPetId: String?){
        viewModelScope.launch {
            petRepository.fetch().collect {
                pets.value.clear()
                pets.value.addAll(it)
                fetchPhotos(it)

                if(requestPetId != null) {
                    _requestPet.value = pets.value.find { it.id == requestPetId}
                }
            }
        }
    }

    private fun fetchPhotos(pets: List<PetEntity>) {
        viewModelScope.launch {
            pets.forEach {petEntity ->
                petRepository.fetchPetProfilePhoto(petEntity.id).collect {
                    when(it) {
                        is Resources.Error -> {
                            if(it.message == "no_internet") _state.update { PetCalendarActivityState.NO_INTERNET }
                            if(it.message == "internal_error") _state.update { PetCalendarActivityState.ERROR }
                        }
                        is Resources.Loading -> {
                            _state.update { PetCalendarActivityState.LOADING }
                        }
                        is Resources.Success -> {
                            _petProfilePhotos.value.put(petEntity.id, it.data as ByteArray)
                        }
                    }
                }
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