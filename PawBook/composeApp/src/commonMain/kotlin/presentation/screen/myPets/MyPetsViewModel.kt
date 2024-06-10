package presentation.screen.petDetail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.entity.PetEntity
import data.repository.PetsRepoitoryImpl
import domain.model.enums.MyPetsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MyPetsViewModel() : ViewModel(), KoinComponent {

    var pets = mutableStateListOf<PetEntity>()
    private val _state = MutableStateFlow(MyPetsState.IDLE)
    val state: StateFlow<MyPetsState> = _state
    private val petsRepository: PetsRepoitoryImpl by inject()

    fun fetch() {

        viewModelScope.launch {
            petsRepository.fetch().collect {
                _state.update { MyPetsState.SUCCESS }
                it.let { it1 -> pets.addAll(it1) }

            }
        }
    }
}
