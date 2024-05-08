package presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.LoginRepositoryImpl
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoginViewModel() : ViewModel(), KoinComponent {
    val loginRepository: LoginRepositoryImpl by inject()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email, password)
        }
    }

    fun register(email: String, password: String, repeatPassword: String) {

    }

    fun loginFacebook() {

    }

    fun loginGoogle() {

    }

    fun loginApple() {

    }
}
