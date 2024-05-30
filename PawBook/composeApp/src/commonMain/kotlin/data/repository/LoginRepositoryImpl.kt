package data.repository

import core.util.Resources
import data.remote.LoginApi
import data.local.Preferences
import domain.model.result.LoginResult
import domain.model.result.Tokens
import domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginRepositoryImpl : LoginRepository, KoinComponent {
    private val ERROR_INVALID_LOGIN = "Incorrect email or password"

    private val loginApi: LoginApi by inject()
    private val preferences: Preferences by inject()

    override suspend fun login(email: String, password: String): Flow<Resources<LoginResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val loginResult = loginApi.login(email, password)
            if (loginResult.message == ERROR_INVALID_LOGIN) emit(Resources.Error("invalid_login"))
            else {
                if(loginResult.user != null) {
                    emit(Resources.Success(loginResult))
                    preferences.saveUser(loginResult.user, loginResult.tokens as Tokens)
                } else {
                    emit(Resources.Error("internal_error"))
                }
            }
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }
}