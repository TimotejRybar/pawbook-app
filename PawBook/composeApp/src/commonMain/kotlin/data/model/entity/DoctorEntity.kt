package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.Address
import domain.model.Location

@Entity
data class DoctorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: Address,
    val skills: List<String>,
    val gps: Location,
    val created: Long,
    val updated: Long
)