package data.remote

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import domain.model.EpilepsyRecord
import domain.model.Pet
import domain.model.WeightRecord
import domain.model.request.UpdatePetRequest
import domain.model.result.CreatePetResult
import domain.model.result.FetchGalleryResult
import domain.model.result.FetchPetsResult
import domain.model.result.UpdatePetResult
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse

interface PetApi
{
    @GET("pets/user")
    suspend fun fetch(): FetchPetsResult

    @POST("pets")
    suspend fun create(@Body pet: Pet): CreatePetResult

    @POST("pets/uploadProfilePhoto/{petId}")
    suspend fun uploadProfilePhoto(@Path("petId") petId: String, @Body file: MultiPartFormDataContent): String?

    @GET("pets/getProfilePhoto/{petId}")
    suspend fun getProfilePhoto(@Path("petId") petId: String): Response<ByteArray>?

    @POST("pets/addWeightRecord/{petId}")
    suspend fun addWeightRecord(@Path("petId") petId: String, @Body weightRecord: WeightRecord): WeightRecord

    @POST("pets/addEpilepsyRecord/{petId}")
    suspend fun addEpilepsyRecord(@Path("petId") petId: String, @Body epilepsyRecord: EpilepsyRecord): EpilepsyRecord

    @PUT("pets")
    suspend fun update(@Body updatePetRequest: UpdatePetRequest): UpdatePetResult

    @GET("pets/gallery")
    suspend fun fetchGallery(): FetchGalleryResult

    @Multipart
    @POST("pets/uploadGalleryItem")
    suspend fun uploadGalleryItem(@Body multipart: MultiPartFormDataContent): HttpResponse
}