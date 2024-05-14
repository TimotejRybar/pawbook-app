package data.remote

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import domain.model.result.CreatePetResult
import domain.model.result.FetchPetsResult
import domain.model.result.RegisterResult

interface PetsApi
{
    @GET("pets/user")
    suspend fun fetch(): FetchPetsResult

    @FormUrlEncoded
    @POST("pets")
    suspend fun create(@Field name: String, @Field shortDescription: String): CreatePetResult

}