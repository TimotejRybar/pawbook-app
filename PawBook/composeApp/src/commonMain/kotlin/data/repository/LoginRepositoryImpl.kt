package data.repository

import core.util.Resources
import data.remote.LoginApi
import domain.model.result.LoginResult
import domain.model.result.RegisterResult
import domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginRepositoryImpl : LoginRepository, KoinComponent {
    private val ERROR_INVALID_LOGIN = "Incorrect email or password"

    private val loginApi: LoginApi by inject()
    override suspend fun login(email: String, password: String): Flow<Resources<LoginResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val loginResult = loginApi.login(email, password)
            if (loginResult.message == ERROR_INVALID_LOGIN) emit(Resources.Error("invalid_login"))
            emit(Resources.Success(loginResult))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override suspend fun register(email: String, password: String): Flow<Resources<RegisterResult>> = flow {
        emit(Resources.Loading(true))
        //val registerResult = loginApi.register(RegisterRequest(email, password))
        //emit(Resources.Success(registerResult))
    }
}