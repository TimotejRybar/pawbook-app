package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EpilepsyRecord (
    val _id: String?,
    val barfing: Boolean,
    val drooling: Boolean,
    val fainting: Boolean,
    val spasms: Int, // intensity 0-3
    val duration: Int, // minutes
    val note: String = "",
    val created: LocalDateTime
) {
    fun calculatePoints(): Double {
        // TODO: calculate points
        return 4.0
    }
}