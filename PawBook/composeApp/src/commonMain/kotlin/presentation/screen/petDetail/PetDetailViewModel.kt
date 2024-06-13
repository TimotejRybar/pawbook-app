package presentation.screen.petDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.readByteArray
import core.util.Resources
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import data.repository.PetDetailRepositoryImpl
import domain.model.Pet
import domain.model.enums.PetDetailState
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

    val pet = mutableStateOf(Pet.empty())

    private val _profilePicture = MutableStateFlow("")
    val profilePicture: StateFlow<String> = _profilePicture

    private val _breeds = MutableStateFlow<ArrayList<BreedEntity>>(arrayListOf())
    val breeds: StateFlow<ArrayList<BreedEntity>> = _breeds

    private val _doctors = MutableStateFlow<ArrayList<DoctorEntity>>(arrayListOf())
    val doctors: StateFlow<ArrayList<DoctorEntity>> = _doctors

    private val _colors = MutableStateFlow<ArrayList<ColorEntity>>(arrayListOf())
    val colors: StateFlow<ArrayList<ColorEntity>> = _colors

    private val _petColors = MutableStateFlow<ArrayList<ColorEntity>>(arrayListOf())
    val petColors: StateFlow<ArrayList<ColorEntity>> = _petColors

    private val _petDoctor = MutableStateFlow<DoctorEntity?>(null)
    val petDoctor: StateFlow<DoctorEntity?> = _petDoctor


    fun init() {
        fetchBreeds()
        fetchDoctors()
        fetchColors()
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

    fun loadPetDoctor(petEntity: PetEntity) {
        viewModelScope.launch {
            petDetailRepository.loadPetDoctor(petEntity).collect {
                _petDoctor.value = it
            }
        }
    }

    fun loadPetColors(petEntity: PetEntity) {
        viewModelScope.launch {
            petDetailRepository.loadPetColors(petEntity).collect {
                _petColors.value.clear()
                _petColors.value.addAll(it)
            }
        }
    }

    fun createPet(pet: Pet) {
        viewModelScope.launch {
            petDetailRepository.createPet(pet).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetDetailState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetDetailState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetDetailState.LOADING }
                    }
                    is Resources.Success -> {
                        _state.update { PetDetailState.SAVED }
                    }
                }
            }
        }
    }

    fun uploadProfilePicture(context: PlatformContext, pet: PetEntity?, fileBytes: List<KmpFile>) {
        viewModelScope.launch {
            val file = fileBytes.firstOrNull()?.readByteArray(context)
            petDetailRepository.uploadProfilePicture(pet?.id as String, file).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetDetailState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetDetailState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetDetailState.LOADING }
                    }
                    is Resources.Success -> {
                        _profilePicture.value = it.data ?: ""
                        _state.update { PetDetailState.UPLOADED_PHOTO }
                    }
                }
            }
        }
    }
}
