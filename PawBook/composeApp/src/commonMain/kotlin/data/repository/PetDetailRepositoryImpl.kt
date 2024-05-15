package data.repository

import core.util.Resources
import data.remote.BreedApi
import domain.model.PetItem
import domain.model.enums.PetType
import domain.model.result.FetchBreedsResult
import domain.repository.PetDetailRepository
import domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDetailRepositoryImpl: PetDetailRepository, KoinComponent {

    private val breedApi: BreedApi by inject()
    override suspend fun fetchBreeds(petType: PetType): Flow<Resources<FetchBreedsResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = breedApi.fetch(petType)
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

}