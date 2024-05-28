package data.remote

import de.jensklingenberg.ktorfit.http.GET
import domain.model.result.FetchColorsResult

interface ColorApi
{
    @GET("colors")
    suspend fun fetch(): FetchColorsResult

}