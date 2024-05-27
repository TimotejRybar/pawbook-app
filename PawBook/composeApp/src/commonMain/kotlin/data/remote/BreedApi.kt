package data.remote

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import domain.model.enums.PetType
import domain.model.result.CreatePetResult
import domain.model.result.FetchBreedsResult
import domain.model.result.FetchPetsResult
import domain.model.result.RegisterResult

interface BreedApi
{
    @GET("breeds")
    suspend fun fetch(): FetchBreedsResult

}