package domain.repository

import core.util.Resources
import data.model.entity.StorageEntryEntity
import domain.model.enums.StorageEntryType
import domain.model.result.CreateFolderResult
import domain.model.result.UploadFileResult
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun fetch(currentPath: String): Flow<List<StorageEntryEntity>>
    suspend fun uploadFile(name: String?, file: ByteArray, fileType: StorageEntryType, vPath: String): Flow<Resources<UploadFileResult>>
    suspend fun createFolder(name: String?, vPath: String): Flow<Resources<CreateFolderResult>>
}