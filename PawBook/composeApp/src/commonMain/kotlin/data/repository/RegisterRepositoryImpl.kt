package data.repository

import com.russhwolf.settings.Settings
import core.util.Resources
import data.remote.LoginApi
import data.remote.Preferences
import domain.model.result.RegisterResult
import domain.model.result.Tokens
import domain.model.result.User
import domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

enum class SocialLogin {
    none,
    facebook,
    google,
}
class RegisterRepositoryImpl : RegisterRepository, KoinComponent {

    private val loginApi: LoginApi by inject()
    private val preferences: Preferences by inject()

    override suspend fun register(name: String, password: String, email: String): Flow<Resources<RegisterResult>> = flow {
        emit(Resources.Loading(true))
        val registerResult = loginApi.register(name, password, email, SocialLogin.none.name)
        preferences.saveUser(registerResult.user as User, registerResult.accessAndRefreshTokens as Tokens)
        emit(Resources.Success(registerResult))
    }
}