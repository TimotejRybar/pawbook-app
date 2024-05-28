package core.util

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

actual object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    actual override val descriptor: SerialDescriptor
        get() = TODO("Not yet implemented")

    actual override fun serialize(
        encoder: Encoder,
        value: LocalDateTime
    ) {
    }

    actual override fun deserialize(decoder: Decoder): LocalDateTime {
        TODO("Not yet implemented")
    }
}