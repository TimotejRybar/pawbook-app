package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.Address
import domain.model.Location
import domain.model.enums.PetType

@Entity
data class DoctorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: Address,
    val skills: List<PetType>,
    val gps: Location,
    val created: Long,
    val updated: Long
)