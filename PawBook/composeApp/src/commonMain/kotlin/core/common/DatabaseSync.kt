package core.common

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.remote.BreedApi
import data.remote.ColorApi
import data.remote.DoctorApi
import data.repository.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

enum class DatabaseVersion(val value: Int) {
    BETA(1),
    VANILLA(2);

    companion object {
        fun fromInt(value: Int) = entries.first { it.value == value }

        fun DatabaseVersion.toInt() = entries.first { it.value == value }

    }
}

class DatabaseSync: KoinComponent {

    private val breedApi: BreedApi by inject()
    private val doctorApi: DoctorApi by inject()
    private val colorApi: ColorApi by inject()
    private val database: AppDatabase by inject()

    suspend fun synchronize(): Flow<Resources<DatabaseVersion>> = flow {
        emit(Resources.Loading(true))
        // fetch all and add to db if doesnt exist or if remote updated > local updated

        try {
            val breeds = breedApi.fetch()
            val colors = colorApi.fetch()
            val doctors = doctorApi.fetch()
            // update db
            val petBreeds = breeds.breeds.map {
                BreedEntity(it._id, it.petType, it.name, it.key, it.updatedAt, it.createdAt)
            }
            val petColors = colors.colors.map {
                ColorEntity(
                    it._id,
                    it.petType,
                    it.name,
                    it._id,
                    it.color,
                    it.createdAt,
                    it.updatedAt
                )
            }
            val petDoctors = doctors.doctors.map {
                DoctorEntity(
                    it._id,
                    it.name,
                    it.address,
                    it.skills,
                    it.gps,
                    it.createdAt,
                    it.updatedAt
                )
            }
            // save all
            database.getBreedDao().insertAll(petBreeds)
            database.getColorDao().insertAll(petColors)
            database.getDoctorDao().insertAll(petDoctors)
            emit(Resources.Success(Config.CURRENT_DATABASE_VERSION))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }
}