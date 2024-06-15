package presentation.screen.petCreate

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
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

    private val _petProfilePhotos = MutableStateFlow<MutableMap<String, ByteArray>>(mutableMapOf())
    val petProfilePhotos: StateFlow<Map<String, ByteArray>> = _petProfilePhotos

    fun fetch() {

        viewModelScope.launch {
            petsRepository.fetch().collect {
                _state.update { MyPetsState.SUCCESS }
                pets.clear()
                it.let { it1 -> pets.addAll(it1) }
                fetchPhotos(it)
            }
        }
    }

    private fun fetchPhotos(pets: List<PetEntity>) {
        viewModelScope.launch {
            pets.forEach {petEntity ->
                petsRepository.fetchPetProfilePhoto(petEntity.id).collect {
                    when(it) {
                        is Resources.Error -> {
                            if(it.message == "no_internet") _state.update { MyPetsState.NO_INTERNET }
                            if(it.message == "internal_error") _state.update { MyPetsState.ERROR }
                        }
                        is Resources.Loading -> {
                            _state.update { MyPetsState.LOADING }
                        }
                        is Resources.Success -> {
                            _petProfilePhotos.value.put(petEntity.id, it.data as ByteArray)
                        }
                    }
                }
            }
        }
    }

}
