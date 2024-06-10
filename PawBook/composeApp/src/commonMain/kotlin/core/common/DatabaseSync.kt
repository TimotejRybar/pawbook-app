package core.common

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.BreedEntity
import data.model.entity.CalendarActivityEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import data.model.entity.StorageEntryEntity
import data.remote.BreedApi
import data.remote.CalendarActivityApi
import data.remote.ColorApi
import data.remote.DoctorApi
import data.remote.PetApi
import data.remote.StorageApi
import data.repository.NetworkException
import domain.model.enums.StorageEntryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime
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
    private val petApi: PetApi by inject()
    private val calendarApi: CalendarActivityApi by inject()
    private val storageApi: StorageApi by inject()

    private val database: AppDatabase by inject()

    suspend fun synchronizeData(): Flow<Resources<String>> = flow {
        try {
            val pets = petApi.fetch()
            val calendarActivities = calendarApi.fetch()
            //val petPhotos = petPhotoApi.fetch()
            val allFiles = storageApi.fetch();

            // figure out how to save weight to remote db
            val myPets = pets.pets.map {
                PetEntity(it._id as String, it.petType, it.name, it.shortDescription, it.gender, it.birthday, it.weight, it.color, it.breed, it.doctor,it.photo ?: "", arrayListOf(), it.createdAt as LocalDateTime, it.updatedAt as LocalDateTime)
            }

            val activities = calendarActivities.calendar.map {
                CalendarActivityEntity(it._id as String, it.pets, it.start as LocalDateTime, it.end as LocalDateTime, it.activityType, it.location, it.description, it.createdAt as LocalDateTime, it.updatedAt as LocalDateTime)
            }

            //val gallery = petPhotos.gallery.map {
            //    PetPhotoEntity(it._id, it.author as String, it.pets, it.description, it.file, it.created, it.updated)
            //}

            val storage = allFiles.storage.map {
                StorageEntryEntity(it._id as String, StorageEntryType.fromValue(it.storageEntryType), it.name, it.vPath, it.storageKey, it.createdAt as LocalDateTime, it.updatedAt as LocalDateTime)
            }

            database.getPetDao().insertAll(myPets)
            database.getCalendarDao().insertAll(activities)
            database.getStorageDao().insertAll(storage)
            //database.getPetPhotoDao().insertAll(gallery)
            emit(Resources.Success(""))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun synchronizeResources(): Flow<Resources<DatabaseVersion>> = flow {
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
                ColorEntity(it._id, it.petType, it.name, it._id, it.color, it.createdAt, it.updatedAt)
            }
            val petDoctors = doctors.doctors.map {
                DoctorEntity(it._id, it.name, it.address, it.skills, it.gps, it.createdAt, it.updatedAt)
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