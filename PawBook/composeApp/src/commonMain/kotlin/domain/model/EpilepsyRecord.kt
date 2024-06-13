package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EpilepsyRecord (
    val _id: String = "1",
    var barfing: Boolean = true,
    var drooling: Boolean = true,
    var fainting: Boolean = false,
    var spasms: Int = 1, // intensity 0-3
    var duration: Int = 2, // minutes
    var note: String = "",
    val created: LocalDateTime = LocalDateTime(1,1,1,1,1,1)
) {
    fun calculatePoints(): Double {

    }
}