package core.util

import androidx.compose.ui.graphics.Color

actual object HexColorConverter {
    actual fun convert(hex: String): Color {
        val cleanedHex = hex.removePrefix("#")
        val colorInt = cleanedHex.toLong(16)
        val alpha = if (cleanedHex.length == 8) {
            (colorInt shr 24 and 0xFF).toInt()
        } else {
            255
        }
        val red = (colorInt shr 16 and 0xFF).toInt()
        val green = (colorInt shr 8 and 0xFF).toInt()
        val blue = (colorInt and 0xFF).toInt()
        return Color(red, green, blue, alpha)
    }
}
