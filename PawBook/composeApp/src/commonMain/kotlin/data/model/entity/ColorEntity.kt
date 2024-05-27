package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ColorEntity(
    @PrimaryKey val id: String,
    val petType: String,
    val name: String,
    val key: String,
    val color: String, // HEX color
    val created: Long,
    val updated: Long
)