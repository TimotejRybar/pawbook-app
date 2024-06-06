package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.StorageEntryEntity
import data.remote.StorageApi
import domain.model.StorageEntry
import domain.model.enums.StorageEntryType
import domain.model.result.CreateFolderResult
import domain.model.result.UploadFileResult
import domain.repository.StorageRepository
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageRepositoryImpl : StorageRepository, KoinComponent {

    private val database: AppDatabase by inject()
    private val storageApi: StorageApi by inject()

    override suspend fun fetch(): Flow<List<StorageEntryEntity>> {
        return database.getStorageDao().getAllAsFlow()
    }

    override suspend fun uploadFile(
        name: String?,
        file: ByteArray,
        fileType: StorageEntryType,
        vPath: String
    ): Flow<Resources<UploadFileResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val multipart = MultiPartFormDataContent(formData {
                append("name", name as String)
                append("fileType", fileType.value)
                append("vPath", vPath)
                append("image", file, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=image.png")
                })
            })

            val result = storageApi.uploadFile(multipart)

            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun createFolder(
        name: String?,
        vPath: String
    ): Flow<Resources<CreateFolderResult>> = flow {
        emit(Resources.Loading(true))

        val createFolderResult = storageApi.createFolder(
            StorageEntry(null, StorageEntryType.FOLDER, name as String,
                vPath, "", null, null))

        createFolderResult.folder.let {
            database.getStorageDao().insert(StorageEntryEntity(it._id as String, it.storageEntryType, it.name, it.vPath, it.storageKey, it.createdAt, it.updatedAt))
        }

        emit(Resources.Success(createFolderResult))
    }
}