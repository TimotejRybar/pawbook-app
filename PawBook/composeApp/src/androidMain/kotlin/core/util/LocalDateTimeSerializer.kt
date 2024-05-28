package core.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

actual object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    actual override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    actual override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val encoded = formatter.format(value.toJavaLocalDateTime())
        encoder.encodeString(encoded)
    }

    actual override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        //val date = formatter.parse(string)
        val parsed = formatter.format(string)
        val result = parsed.replaceFirst(Regex("\\..*"), "")
        return LocalDateTime.parse(result)
    }
}
