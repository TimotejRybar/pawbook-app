package data.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import domain.model.enums.PetType
import domain.model.result.FetchDoctorsResult

interface DoctorApi
{
    @GET("doctors")
    suspend fun fetch(@Query("petType") petType: PetType): FetchDoctorsResult

}