package domain.model

import domain.Selectable
import kotlinx.serialization.Serializable

@Serializable
data class Doctor(val address: Address, val skills: List<String>, val gps: Location,
                  override val name: String,
                  override val id: String,
                  override val key: String
): Selectable