package presentation.screen.petDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import data.repository.PetDetailRepositoryImpl
import domain.model.PetBreed
import domain.model.enums.PetDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDetailViewModel(savedStateHandle: SavedStateHandle = SavedStateHandle()) : ViewModel(), KoinComponent {
    private val petDetailRepository: PetDetailRepositoryImpl by inject()

    private val _state = MutableStateFlow(PetDetailState.INIT)
    val state: StateFlow<PetDetailState> = _state

    private val petId: String = checkNotNull(savedStateHandle["petId"])
    var breeds = mutableListOf<PetBreed>()

    fun init() {
        if(petId.equals("CREATE")) {
            fetchBreeds {
                breeds.clear()
                breeds.addAll(it)
            }
        }
    }

    fun fetchBreeds(onPrepared: (List<PetBreed>) -> Unit) {

        viewModelScope.launch {
            petDetailRepository.fetchBreeds().collect { it ->
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { PetDetailState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { PetDetailState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { PetDetailState.LOADING }
                    }
                    is Resources.Success -> {
                        onPrepared(it.data?.breeds as List<PetBreed>)
                    }
                }
            }
        }
    }
}
