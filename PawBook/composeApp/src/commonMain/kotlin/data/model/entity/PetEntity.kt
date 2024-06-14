package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import core.enums.PetType
import domain.model.EpilepsyRecord
import domain.model.WeightRecord
import kotlinx.datetime.LocalDateTime

@Entity
data class PetEntity(
    @PrimaryKey var id: String,
    val petType: PetType,
    var name: String,
    val shortDescription: String?,
    val gender: String,
    val birthDay: LocalDateTime?,
    var weight: Float,
    val color: ArrayList<String>,
    var breed: String,
    val doctor: String?,
    val photo: String?,
    val trackWeight: Boolean,
    val weightHistory: ArrayList<WeightRecord>,
    val trackEpilepsy: Boolean,
    val epilepsyHistory: ArrayList<EpilepsyRecord>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)