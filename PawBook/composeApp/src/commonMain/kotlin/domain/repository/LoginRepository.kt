package domain.repository

import core.util.Resources
import domain.model.result.BaseResult
import domain.model.result.LoginResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): Flow<Resources<LoginResult>>

    suspend fun register(email: String, password: String): Flow<Resources<BaseResult>>
}
