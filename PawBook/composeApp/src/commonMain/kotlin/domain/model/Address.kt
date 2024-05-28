package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(val street: String? = null, val city: String? = null, val postalCode: String? = null)