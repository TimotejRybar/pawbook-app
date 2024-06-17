package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.PetPhotoEntity
import data.remote.PetApi
import domain.model.result.CreatePetPhotoResult
import domain.repository.GalleryRepository
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screen.gallery.FileData

class GalleryRepositoryImpl: GalleryRepository, KoinComponent {

    private val database: AppDatabase by inject()
    private val petApi: PetApi by inject()

    override suspend fun create(petPhoto: PetPhotoEntity): Flow<Resources<Boolean>> = flow {
        emit(Resources.Loading(true))
        try {
            // TODO
            emit(Resources.Success(true))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override suspend fun fetch(): Flow<List<PetPhotoEntity>> {
        return database.getPetPhotoDao().getAllAsFlow()
    }

    override suspend fun uploadFiles(filesData: ArrayList<FileData>): Flow<Resources<Boolean>> = flow {
        emit(Resources.Loading(true))
        try {
            val multipart = MultiPartFormDataContent(formData {
                append("description", filesData.first().description)
                append("pets", filesData.first().pets.joinToString(","))

                filesData.forEach { fileData ->
                    append("name", fileData.name)
                    append("mediaType", fileData.mediaType.name)
                    append(
                        "files",
                        InputProvider { buildPacket { writeFully(fileData.data) } },
                        Headers.build {
                            append(HttpHeaders.ContentType, fileData.mediaType.toString())
                            append(HttpHeaders.ContentDisposition, "filename=${fileData.name}")
                        }
                    )
                }
            })

            val response: HttpResponse = petApi.uploadGalleryItem(multipart)
            val result = Json.decodeFromString<CreatePetPhotoResult>(response.bodyAsText())
            emit(Resources.Success(true))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }
}