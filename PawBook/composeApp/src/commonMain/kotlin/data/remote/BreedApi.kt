package data.remote

import de.jensklingenberg.ktorfit.http.GET
import domain.model.result.FetchBreedsResult

interface BreedApi
{
    @GET("breeds")
    suspend fun fetch(): FetchBreedsResult

}