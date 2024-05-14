package presentation.screen.petDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
import core.util.LocalDateTimeSerializer
import core.util.Resources
import data.remote.Preferences
import data.repository.LoginRepositoryImpl
import data.repository.PetDetailRepositoryImpl
import domain.model.PetBreed
import domain.model.enums.LoginState
import domain.model.enums.PetDetailAction
import domain.model.enums.PetDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PetDetailViewModel() : ViewModel(), KoinComponent {
    private val petDetailRepository: PetDetailRepositoryImpl by inject()

    private val _actionType = MutableStateFlow(PetDetailAction.VIEW)
    val actionType: StateFlow<PetDetailAction> = _actionType
    private val _state = MutableStateFlow(PetDetailState.INIT)
    val state: StateFlow<PetDetailState> = _state

    fun init() {
        if(_actionType.value == PetDetailAction.EDIT) {
            fetchBreeds {

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
