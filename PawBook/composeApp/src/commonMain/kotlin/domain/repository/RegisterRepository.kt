package domain.repository

import core.util.Resources
import domain.model.result.RegisterResult
import domain.model.result.Tokens
import domain.model.result.User
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(name: String, password: String, email: String): Flow<Resources<RegisterResult>>
}
