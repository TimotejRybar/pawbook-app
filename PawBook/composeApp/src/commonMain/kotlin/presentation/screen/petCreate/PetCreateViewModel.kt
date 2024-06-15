package presentation.screen.petCreate

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
import domain.model.enums.PetCreateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetCreateViewModel() : ViewModel(), KoinComponent {

    private val petDetailRepository: PetDetailRepositoryImpl by inject()

    private val _state = MutableStateFlow(PetCreateState.INIT)
    val state: StateFlow<PetCreateState> = _state

    private val _pet = MutableStateFlow<PetEntity?>(null)
    val pet: StateFlow<PetEntity?> = _pet

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

    private val _petBreed = MutableStateFlow<BreedEntity?>(null)
    val petBreed: StateFlow<BreedEntity?> = _petBreed

    fun init() {
        fetchBreeds()
        fetchDoctors()
        fetchColors()
    }

    private fun fetchBreeds() {
        // get from database
        viewModelScope.launch {
            petDetailRepository.fetchBreeds().collect {
                breeds.value.clear()
                breeds.value.addAll(it)
            }
        }
    }

    private fun fetchColors() {
        viewModelScope.launch {
            petDetailRepository.fetchColors().collect {
                colors.value.clear()
                colors.value.addAll(it)
            }
        }
    }

    private fun fetchDoctors() {
        viewModelScope.launch {
            petDetailRepository.fetchDoctors().collect {
                doctors.value.clear()
                doctors.value.addAll(it)
            }
        }
    }

    fun loadPet(petId: String) {
        if(petId == "") return
        viewModelScope.launch {
            petDetailRepository.loadPet(petId).collect {
                _pet.value = it
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

    fun loadPetBreed(petEntity: PetEntity) {
        viewModelScope.launch {
            petDetailRepository.loadPetBreed(petEntity).collect {
                _petBreed.value = it
            }
        }
    }

    fun loadPetColors(petEntity: PetEntity) {
        viewModelScope.launch {
            petDetailRepository.loadPetColors(petEntity).collect {
                _petColors.value = ArrayList(it)
            }
        }
    }

    fun createPet(pet: Pet) {
        viewModelScope.launch {
            petDetailRepository.createPet(pet).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetCreateState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetCreateState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetCreateState.LOADING }
                    }
                    is Resources.Success -> {
                        _state.update { PetCreateState.SAVED }
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
                        if(it.message == "no_internet") _state.update { PetCreateState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetCreateState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetCreateState.LOADING }
                    }
                    is Resources.Success -> {
                        _profilePicture.value = it.data ?: ""
                        _state.update { PetCreateState.UPLOADED_PHOTO }
                    }
                }
            }
        }
    }

    fun updatePet(pet: PetEntity?, petData: Pet) {
        viewModelScope.launch {
            petDetailRepository.updatePet(pet, petData).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetCreateState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetCreateState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetCreateState.LOADING }
                    }
                    is Resources.Success -> {
                        _state.update { PetCreateState.SAVED }
                    }
                }
            }
        }
    }
}
