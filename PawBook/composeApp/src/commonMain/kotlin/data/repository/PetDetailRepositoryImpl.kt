package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import data.remote.PetApi
import domain.model.Pet
import domain.model.WeightRecord
import domain.model.result.CreatePetPhotoResult
import domain.model.result.CreatePetResult
import domain.repository.PetDetailRepository
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDetailRepositoryImpl: PetDetailRepository, KoinComponent {

    private val petApi: PetApi by inject()
    private val database: AppDatabase by inject()

    override suspend fun fetchBreeds(): Flow<List<BreedEntity>> {
        return database.getBreedDao().getAllAsFlow()
    }

    override suspend fun fetchDoctors(): Flow<List<DoctorEntity>> {
        return database.getDoctorDao().getAllAsFlow()
    }

    override suspend fun fetchColors(): Flow<List<ColorEntity>> {
        return database.getColorDao().getAllAsFlow()
    }

    override suspend fun createPet(pet: Pet): Flow<Resources<CreatePetResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = petApi.create(pet)
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun uploadProfilePicture(petId: String, file: ByteArray?): Flow<Resources<String>> = flow {
        emit(Resources.Loading(true))
        try {
            val multipart = MultiPartFormDataContent(formData {
                append("description", "Ktor logo")
                append("image", file as ByteArray, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=image.png")
                })
            })

            val result =  Json.decodeFromString<CreatePetPhotoResult>(petApi.uploadProfilePhoto(petId, multipart) as String)
            emit(Resources.Success(result.photo))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun addWeightRecord(petId: String, weightRecord: WeightRecord): Flow<Resources<WeightRecord>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = petApi.addWeightRecord(petId, weightRecord)
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun loadPetDoctor(petEntity: PetEntity): Flow<DoctorEntity> {
        return database.getDoctorDao().getById(petEntity.doctor.toString())
    }

    override suspend fun loadPetColors(petEntity: PetEntity): Flow<List<ColorEntity>> {
        return database.getColorDao().getFromHexStrings(petEntity.color)
    }

    override suspend fun loadPet(petId: String): Flow<PetEntity> {
        return database.getPetDao().getById(petId)
    }

}