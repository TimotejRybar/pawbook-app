package domain.model.result

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserProfilePhotoResult(val message: String, val photo: String)