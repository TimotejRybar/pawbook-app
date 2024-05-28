package presentation.screen.petDetail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import data.repository.PetsRepoitoryImpl
import domain.model.PetItem
import domain.model.enums.MyPetsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MyPetsViewModel() : ViewModel(), KoinComponent {

    var pets = mutableStateListOf<PetItem>()
    private val _state = MutableStateFlow(MyPetsState.IDLE)
    val state: StateFlow<MyPetsState> = _state
    private val petsRepository: PetsRepoitoryImpl by inject()

    fun fetch() {

        viewModelScope.launch {
            petsRepository.fetch().collect { it ->
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { MyPetsState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { MyPetsState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { MyPetsState.LOADING }
                    }
                    is Resources.Success -> {
                        pets.clear()
                        _state.update { MyPetsState.SUCCESS }
                        it.data?.let { it1 -> pets.addAll(it1) }
                    }
                }
            }
        }
    }
}
