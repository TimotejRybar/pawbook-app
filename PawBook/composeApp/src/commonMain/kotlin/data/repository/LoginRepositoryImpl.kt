package data.repository

import core.util.Resources
import data.remote.LoginApi
import domain.model.request.LoginRequest
import domain.model.request.RegisterRequest
import domain.model.result.BaseResult
import domain.model.result.LoginResult
import domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginRepositoryImpl : LoginRepository, KoinComponent {
    private val loginApi: LoginApi by inject()
    override suspend fun login(email: String, password: String): Flow<Resources<LoginResult>> = flow {
        emit(Resources.Loading(true))
        val loginResult = loginApi.login(LoginRequest(email, password))
        emit(Resources.Success(loginResult))
    }

    override suspend fun register(email: String, password: String): Flow<Resources<BaseResult>> = flow {
        emit(Resources.Loading(true))
        val registerResult = loginApi.register(RegisterRequest(email, password))
        emit(Resources.Success(registerResult))
    }
}