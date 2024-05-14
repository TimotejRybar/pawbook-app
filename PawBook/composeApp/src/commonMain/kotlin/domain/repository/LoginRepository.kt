package domain.repository

import core.util.Resources
import domain.model.result.LoginResult
import domain.model.result.RegisterResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): Flow<Resources<LoginResult>>
}
