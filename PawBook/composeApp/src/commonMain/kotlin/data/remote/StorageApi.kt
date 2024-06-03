package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import domain.model.PetItem
import domain.model.result.CreatePetPhotoResult
import domain.model.result.FetchGalleryResult
import io.ktor.client.request.forms.MultiPartFormDataContent

interface StorageApi {
    @GET("storage")
    suspend fun fetch(): FetchGalleryResult

    @POST("storage")
    suspend fun create(@Body pet: PetItem): CreatePetPhotoResult

    @POST("storage/uploadeUserProfilePicture")
    suspend fun uploadUserProfilePicture(@Body file: MultiPartFormDataContent): String

}