package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.remote.PetApi
import domain.model.PetItem
import domain.model.PetPhoto
import domain.model.result.CreatePetResult
import domain.repository.PetDetailRepository
import io.kamel.core.utils.File
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.headersOf
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.readBytes
import io.ktor.utils.io.core.use
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pawbook.composeapp.generated.resources.Res

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

    override suspend fun createPet(pet: PetItem): Flow<Resources<CreatePetResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = petApi.create(pet)
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun uploadProfilePicture(file: ByteArray?): Flow<Resources<String>> = flow {
        emit(Resources.Loading(true))
        try {
            val multipart = MultiPartFormDataContent(formData {
                append("description", "Ktor logo")
                append("image", file as ByteArray, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=image.png")
                })
            })

            val result = petApi.uploadProfilePhoto(multipart) // Update to pass multipart directly
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

}