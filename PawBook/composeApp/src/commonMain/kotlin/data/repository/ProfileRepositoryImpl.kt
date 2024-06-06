package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.local.Preferences
import data.remote.AuthApi
import data.remote.StorageApi
import domain.model.result.CreateUserProfilePhotoResult
import domain.model.result.User
import domain.repository.ProfileRepository
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileRepositoryImpl: ProfileRepository, KoinComponent {

    private val preferences: Preferences by inject()
    private val database: AppDatabase by inject()
    private val authApi: AuthApi by inject()
    private val storageApi: StorageApi by inject()

    override suspend fun fetchMyProfile(): User {
        return preferences.getUser()
    }

    override suspend fun uploadProfilePicture(profile: User?, file: ByteArray): Flow<Resources<String>> = flow {
        emit(Resources.Loading(true))
        try {
            val multipart = MultiPartFormDataContent(formData {
                append("description", "Ktor logo")
                append("image", file, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=image.png")
                })
            })

            val result =  Json.decodeFromString<CreateUserProfilePhotoResult>(storageApi.uploadUserProfilePicture(multipart) as String)
            emit(Resources.Success(result.photo))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override fun updateProfile(profile: User?): Flow<Resources<User>> = flow {
        try {
            preferences.saveUser(profile as User)
            authApi.updateProfile(profile)
            emit(Resources.Success(profile))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override fun isUserProfilePhotoUploaded(): Boolean {
        return preferences.isUserProfilePhotoUploaded()
    }

    override fun setUserProfilePhotoUploaded(value: Boolean) {
        preferences.setUserProfilePhotoUploaded(value)
    }
}