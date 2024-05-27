package core.common

import core.util.Resources
import data.remote.BreedApi
import data.remote.ColorApi
import data.remote.DoctorApi
import data.repository.PetDetailRepositoryImpl
import domain.model.PetBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

enum class DATABASE_VERSION {
    BETA,
    VANILLA
}

class DatabaseSync: KoinComponent {

    private val breedApi: BreedApi by inject()
    private val doctorApi: DoctorApi by inject()
    private val colorApi: ColorApi by inject()

    suspend fun synchronize(): Flow<Resources<DATABASE_VERSION>> = flow {
        emit(Resources.Loading(true))
        // fetch all and add to db if doesnt exist or if remote updated > local updated
        val breeds = breedApi.fetch()
        val colors = colorApi.fetch()
        val doctors = doctorApi.fetch()
        // update db
        breeds.breeds.map {
            PetBreed(it.petType, it.id, it.name, it.key)
        }

        emit(Resources.Success(Config.CURRENT_DATABASE_VERSION))
    }
}