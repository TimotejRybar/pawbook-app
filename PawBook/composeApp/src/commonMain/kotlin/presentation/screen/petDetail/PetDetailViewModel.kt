package presentation.screen.petDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import core.util.Resources
import data.local.AppDatabase
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.repository.PetDetailRepositoryImpl
import domain.model.Doctor
import domain.model.PetBreed
import domain.model.PetColor
import domain.model.PetItem
import domain.model.enums.PetDetailState
import domain.model.enums.PetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDetailViewModel() : ViewModel(), KoinComponent {
    private val petDetailRepository: PetDetailRepositoryImpl by inject()

    private val _state = MutableStateFlow(PetDetailState.INIT)
    val state: StateFlow<PetDetailState> = _state

    val pet = mutableStateOf(PetItem.empty())

    private val _breeds = MutableStateFlow<ArrayList<BreedEntity>>(arrayListOf())
    val breeds: StateFlow<ArrayList<BreedEntity>> = _breeds

    private val _doctors = MutableStateFlow<ArrayList<DoctorEntity>>(arrayListOf())
    val doctors: StateFlow<ArrayList<DoctorEntity>> = _doctors

    private val _colors = MutableStateFlow<ArrayList<ColorEntity>>(arrayListOf())
    val colors: StateFlow<ArrayList<ColorEntity>> = _colors


    fun init() {
        if(pet.value.id == "CREATE") {
            fetchBreeds()
            fetchDoctors()
            fetchColors()
        }
    }

    private fun fetchBreeds() {
        // get from database
        viewModelScope.launch {
            petDetailRepository.fetchBreeds().collect {
                breeds.value.addAll(it)
            }
        }
    }

    private fun fetchColors() {
        viewModelScope.launch {
            petDetailRepository.fetchColors().collect {
                colors.value.addAll(it)
            }
        }
    }

    private fun fetchDoctors() {
        viewModelScope.launch {
            petDetailRepository.fetchDoctors().collect {
                doctors.value.addAll(it)
            }
        }
    }

    fun createPet(pet: PetItem) {
        viewModelScope.launch {
            petDetailRepository.createPet(pet).collect { it ->
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetDetailState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetDetailState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetDetailState.LOADING }
                    }
                    is Resources.Success -> {
                        // TODO
                    }
                }
            }
        }
    }
}
