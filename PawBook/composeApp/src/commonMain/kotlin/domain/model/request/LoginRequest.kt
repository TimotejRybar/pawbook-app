package domain.model.request

@kotlinx.serialization.Serializable
data class LoginRequest(val email: String, val passsword: String)