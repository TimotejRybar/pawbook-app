package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import domain.model.Pet
import domain.model.WeightRecord
import domain.model.result.CreatePetResult
import domain.model.result.FetchPetsResult
import io.ktor.client.request.forms.MultiPartFormDataContent

interface PetApi
{
    @GET("pets/user")
    suspend fun fetch(): FetchPetsResult

    @POST("pets")
    suspend fun create(@Body pet: Pet): CreatePetResult

    @POST("pets/uploadProfilePhoto/{petId}")
    suspend fun uploadProfilePhoto(@Path("petId") petId: String, @Body file: MultiPartFormDataContent): String?

    @POST("pets/addWeightRecord/{petId}")
    suspend fun addWeightRecord(@Path("petId") petId: String, @Body weightRecord: WeightRecord): WeightRecord

}