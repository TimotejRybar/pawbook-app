package presentation.screen.petDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import data.repository.PetDetailRepositoryImpl
import domain.model.PetBreed
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

    //private val _pet = MutableStateFlow(PetItem.empty())
    //val pet: StateFlow<PetItem> = _pet
    val pet = mutableStateOf(PetItem.empty())

    private val _breeds = MutableStateFlow<ArrayList<PetBreed>>(arrayListOf())
    val breeds: StateFlow<ArrayList<PetBreed>> = _breeds

    fun init() {
        if(pet.value.id == "CREATE") {
            fetchBreeds(pet.value.petType)
        }
    }

    private fun fetchBreeds(petType: PetType) {

        viewModelScope.launch {
            petDetailRepository.fetchBreeds(petType).collect { it ->
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetDetailState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetDetailState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetDetailState.LOADING }
                    }
                    is Resources.Success -> {
                        it.data?.breeds?.let { it1 -> breeds.value.addAll(it1) }

                    }
                }
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
                        //it.data?.pet?.let { it1 -> breeds.value.addAll(it1) }
                    }
                }
            }
        }
    }
}
