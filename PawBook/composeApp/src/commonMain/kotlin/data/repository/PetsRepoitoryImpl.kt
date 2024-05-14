package data.repository

import core.util.Resources
import data.remote.PetsApi
import domain.model.PetItem
import domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetsRepoitoryImpl: PetsRepository, KoinComponent {

    private val petsApi: PetsApi by inject()
    override suspend fun create(pet: PetItem): Flow<Resources<PetItem>> = flow {
        emit(Resources.Loading(true))
        try {
            val fetchResult = petsApi.create(pet.name, pet.shortDescription)
            emit(Resources.Success(fetchResult.pet))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override suspend fun fetch(): Flow<Resources<List<PetItem>>> = flow {
        emit(Resources.Loading(true))
        try {
            val fetchResult = petsApi.fetch()
            emit(Resources.Success(fetchResult.pets))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }
}