package domain.model.request

@kotlinx.serialization.Serializable
data class RegisterRequest(val email: String, val passsword: String)