package data.remote

import de.jensklingenberg.ktorfit.http.GET
import domain.model.result.FetchDoctorsResult

interface DoctorApi
{
    @GET("doctors")
    suspend fun fetch(): FetchDoctorsResult

}