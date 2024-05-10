package data.remote

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST
import domain.model.request.RegisterRequest
import domain.model.result.LoginResult
import domain.model.result.RegisterResult

interface LoginApi
{
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field email: String, @Field password: String): LoginResult

    @FormUrlEncoded
    @POST("register")
    suspend fun register(@Field name: String, @Field password: String, @Field email: String): RegisterResult

}
