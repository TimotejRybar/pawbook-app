package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UserProfileApi {
    @POST("userProfile/uploadProfilePhoto")
    suspend fun uploadProfilePhoto(@Body file: MultiPartFormDataContent): String?
}