package domain.repository

import core.util.Resources
import domain.model.result.FetchBreedsResult
import domain.model.result.LoginResult
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    suspend fun fetchBreeds(): Flow<Resources<FetchBreedsResult>>
}