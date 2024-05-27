package data.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import domain.model.enums.PetType
import domain.model.result.FetchColorsResult
import domain.model.result.FetchDoctorsResult

interface ColorApi
{
    @GET("colors")
    suspend fun fetch(): FetchColorsResult

}