package domain.model

import kotlinx.datetime.LocalDateTime

interface Selectable {
    val id: String
    val name: String
    val key: String
    val updatedAt: LocalDateTime
    val createdAt: LocalDateTime
}