package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(val street: String, val city: String, val postalCode: String)