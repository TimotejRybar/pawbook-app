package domain.repository

import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import core.util.Resources
import domain.model.result.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    suspend fun fetchMyProfile(): User
    suspend fun uploadProfilePicture(profile: User?, file: ByteArray): Flow<Resources<String>>
    fun updateProfile(profile: User?): Flow<Resources<User>>
    fun isUserProfilePhotoUploaded(): Boolean
    fun setUserProfilePhotoUploaded(value: Boolean)
}