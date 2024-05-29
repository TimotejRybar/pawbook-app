package presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.Resources
import data.repository.RegisterRepositoryImpl
import domain.model.enums.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class RegisterViewModel : ViewModel(), KoinComponent {
    private val registerRepoitory: RegisterRepositoryImpl by inject()

    private val _state = MutableStateFlow(RegisterState.IDLE)
    val state: StateFlow<RegisterState> = _state

    fun register(name: String, email: String, password: String, onRegisterAccount: () -> Unit) {

        viewModelScope.launch {
            registerRepoitory.register(name, email, password).collect { it ->
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { RegisterState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { RegisterState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { RegisterState.LOADING }
                    }
                    is Resources.Success -> {
                        // TODO: save token and user in SharedPreferences?
                        _state.update { RegisterState.SUCCESS }
                        onRegisterAccount()
                    }
                }
            }
        }
    }
}
