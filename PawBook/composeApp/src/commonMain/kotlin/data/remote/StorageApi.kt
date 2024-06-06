package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import domain.model.CalendarActivity
import domain.model.StorageEntry
import domain.model.enums.StorageEntryType
import domain.model.result.CreateFolderResult
import domain.model.result.CreateStorageEntryResult
import domain.model.result.FetchStorageResult
import domain.model.result.UploadFileResult
import io.ktor.client.request.forms.MultiPartFormDataContent

interface StorageApi {
    @GET("storage")
    suspend fun fetch(): FetchStorageResult

    @POST("storage")
    suspend fun create(@Body storageEntry: StorageEntry): CreateStorageEntryResult

    @POST("storage/uploadeUserProfilePicture")
    suspend fun uploadUserProfilePicture(@Body file: MultiPartFormDataContent): String

    @POST("storage/uploadFile")
    suspend fun uploadFile(@Body file: MultiPartFormDataContent): UploadFileResult

    @POST("storage/createFolder")
    suspend fun createFolder(@Body storageEntry: StorageEntry): CreateFolderResult
}