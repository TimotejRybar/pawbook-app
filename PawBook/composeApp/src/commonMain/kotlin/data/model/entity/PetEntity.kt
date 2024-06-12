package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import core.enums.PetType
import domain.model.EpilepsyRecord
import domain.model.WeightRecord
import kotlinx.datetime.LocalDateTime

@Entity
data class PetEntity(
    @PrimaryKey val id: String,
    val petType: PetType,
    val name: String,
    val shortDescription: String?,
    val gender: String,
    val birthDay: LocalDateTime?,
    val weight: Float,
    val color: List<String>,
    val breed: String,
    val doctor: String?,
    val photo: String?,
    val trackWeight: Boolean,
    val weightHistory: ArrayList<WeightRecord>,
    val trackEpilepsy: Boolean,
    val epilepsyHistory: ArrayList<EpilepsyRecord>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

