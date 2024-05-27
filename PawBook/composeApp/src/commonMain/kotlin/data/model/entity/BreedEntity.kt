package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreedEntity(
    @PrimaryKey val _id: String,
    val petType: String,
    val name: String,
    val key: String,
    val created: Long,
    val updated: Long
)