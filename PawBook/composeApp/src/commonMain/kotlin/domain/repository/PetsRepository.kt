package domain.repository

import core.util.Resources
import domain.model.PetItem
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    suspend fun create(pet: PetItem): Flow<Resources<PetItem>>
    suspend fun fetch(): Flow<Resources<List<PetItem>>>
}