package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST
import domain.model.result.LoginResult
import domain.model.result.RegisterResult
import domain.model.result.User
import kotlinx.datetime.LocalDateTime

interface AuthApi
{
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field email: String, @Field password: String): LoginResult

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(@Field name: String, @Field password: String,
                         @Field email: String, @Field social: String): RegisterResult

    @POST("auth/updateProfile")
    fun updateProfile(@Body profile: User)
}
