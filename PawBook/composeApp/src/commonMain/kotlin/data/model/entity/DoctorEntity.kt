package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.Address
import domain.model.Location
import domain.model.Selectable
import domain.model.enums.PetType
import kotlinx.datetime.LocalDateTime

@Entity
data class DoctorEntity(
    @PrimaryKey override val id: String,
    override val name: String,
    val address: Address,
    val skills: List<PetType>,
    val gps: Location,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val key: String = id
): Selectable