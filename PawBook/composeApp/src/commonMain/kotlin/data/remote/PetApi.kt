package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import domain.model.PetItem
import domain.model.PetPhoto
import domain.model.result.CreatePetResult
import domain.model.result.FetchPetsResult

interface PetApi
{
    @GET("pets/user")
    suspend fun fetch(): FetchPetsResult

    @POST("pets")
    suspend fun create(@Body pet: PetItem): CreatePetResult

    @POST("pets/{petId}/uploadProfilePhoto")
    suspend fun uploadProfilePhoto(@Path("petId") petId: String, @Body file: ByteArray?): PetPhoto?
}