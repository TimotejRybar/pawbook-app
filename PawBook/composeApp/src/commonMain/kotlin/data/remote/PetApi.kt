package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import domain.model.PetItem
import domain.model.result.CreatePetResult
import domain.model.result.FetchPetsResult

interface PetApi
{
    @GET("pets/user")
    suspend fun fetch(): FetchPetsResult

    @POST("pets")
    suspend fun create(@Body pet: PetItem): CreatePetResult
}