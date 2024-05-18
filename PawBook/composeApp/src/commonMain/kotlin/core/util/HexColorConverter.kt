package core.util

expect object HexColorConverter {
    fun convert(hex: String): androidx.compose.ui.graphics.Color
}

