package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import domain.model.PetItem
import domain.model.result.CreatePetPhotoResult
import domain.model.result.FetchGalleryResult

interface PetPhotoApi {
    @GET("gallery")
    suspend fun fetch(): FetchGalleryResult

    @POST("gallery")
    suspend fun create(@Body pet: PetItem): CreatePetPhotoResult

}