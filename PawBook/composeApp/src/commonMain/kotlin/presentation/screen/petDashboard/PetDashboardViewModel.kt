package presentation.screen.petDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import data.repository.PetDashboardRepositoryImpl
import domain.model.EpilepsyRecord
import domain.model.WeightRecord
import domain.model.enums.PetDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDashboardViewModel() : ViewModel(), KoinComponent {
    private val petDashboardRepository: PetDashboardRepositoryImpl by inject()

    private val _state = MutableStateFlow(PetDetailState.INIT)
    val state: StateFlow<PetDetailState> = _state

    private val _pet = MutableStateFlow<PetEntity?>(null)
    val pet: StateFlow<PetEntity?> = _pet.asStateFlow()

    private val _petColors = MutableStateFlow<List<ColorEntity>?>(arrayListOf())
    val petColors: StateFlow<List<ColorEntity>?> = _petColors.asStateFlow()

    private val _petDoctor = MutableStateFlow<DoctorEntity?>(null)
    val petDoctor: StateFlow<DoctorEntity?> = _petDoctor.asStateFlow()

    fun hexColorsToColorEntities(hexColors: List<String>) {
        viewModelScope.launch {
            petDashboardRepository.loadColors(hexColors).collect {
                _petColors.value = it
            }
        }
    }

    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            petDashboardRepository.loadDoctor(doctorId).collect {
                _petDoctor.value = it
            }
        }
    }

    fun loadPet(petId: String) {
        viewModelScope.launch {
            petDashboardRepository.loadPet(petId).collect {
                _pet.value = it
            }
        }
    }

    suspend fun addWeightRecord(pet: PetEntity, weight: Float) {
        viewModelScope.launch {
            val weightRecord = WeightRecord(null, weight, LocalDateTime(1,1,1,1,1,1))
            petDashboardRepository.addWeightRecord(pet.id, weightRecord).collect {
                // update state if needed...
                when(it){
                    is Resources.Error -> {

                    }
                    is Resources.Loading -> {

                    }
                    is Resources.Success -> {
                        _pet.value?.weightHistory?.add(it.data as WeightRecord)
                    }
                }
            }
        }
    }

    fun addEpilepsyRecord(pet: PetEntity, epilepsyRecord: EpilepsyRecord) {
        TODO("Not yet implemented")
    }
}
