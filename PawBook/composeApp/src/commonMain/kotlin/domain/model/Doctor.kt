package domain.model

import domain.model.enums.PetType
import kotlinx.serialization.Serializable

@Serializable
data class Doctor(val address: Address, val skills: List<PetType>, val gps: Location,
                  override val name: String,
                  override val id: String,
                  override val key: String, override val updated: Long, override val created: Long
): Selectable