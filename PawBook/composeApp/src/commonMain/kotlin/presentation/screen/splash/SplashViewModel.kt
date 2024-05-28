package presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.common.DatabaseSync
import core.util.Resources
import data.repository.PetDetailRepositoryImpl
import domain.model.enums.PetDetailState
import domain.model.enums.SplashState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel() : ViewModel(), KoinComponent {
    private val petDetailRepository: PetDetailRepositoryImpl by inject()
    private val databaseSync: DatabaseSync by inject()

    private val _state = MutableStateFlow(SplashState.IDLE)
    val state: StateFlow<SplashState> = _state

    fun synchronizeDatabase() {
        viewModelScope.launch {
            databaseSync.synchronize().collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { SplashState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { SplashState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { SplashState.LOADING }
                    }
                    is Resources.Success -> {
                        _state.update { SplashState.READY }
                    }
                }
            }
        }
    }
}
