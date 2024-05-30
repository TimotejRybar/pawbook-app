package presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.common.DatabaseSync
import core.util.Resources
import data.repository.LoginRepositoryImpl
import domain.model.enums.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoginViewModel() : ViewModel(), KoinComponent {
    private val loginRepository: LoginRepositoryImpl by inject()
    private val databaseSync: DatabaseSync by inject()

    private val _state = MutableStateFlow(LoginState.IDLE)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String, onLogin: () -> Unit) {

        viewModelScope.launch {
            loginRepository.login(email, password).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { LoginState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { LoginState.ERROR }
                        if(it.message == "invalid_login") _state.update { LoginState.INVALID_LOGIN }
                    }
                    is Resources.Loading -> {
                        _state.update { LoginState.LOADING }
                    }
                    is Resources.Success -> {
                        _state.update { LoginState.SUCCESS }
                        onLogin()
                    }
                }
            }
        }
    }

    fun synchronizeAuthenticated() {
        viewModelScope.launch {
            databaseSync.synchronizeData().collect {

            }
        }
    }
}
