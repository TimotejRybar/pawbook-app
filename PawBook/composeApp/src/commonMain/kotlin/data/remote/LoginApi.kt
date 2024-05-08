package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import domain.model.request.LoginRequest
import domain.model.request.RegisterRequest
import domain.model.result.LoginResult
import domain.model.result.RegisterResult

interface LoginApi {
    @POST("login")
    suspend fun login(@Body body: LoginRequest): LoginResult

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): RegisterResult

}
