package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.Selectable
import kotlinx.datetime.LocalDateTime

@Entity
data class BreedEntity(
    @PrimaryKey override val id: String,
    val petType: String,
    override val name: String,
    override val key: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
): Selectable