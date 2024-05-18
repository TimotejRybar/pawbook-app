package core.util

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import android.graphics.Color as AndroidColor

actual object HexColorConverter {
    actual fun convert(hex: String): Color {
        val androidColor = AndroidColor.parseColor(hex)
        return Color(
            androidColor.red / 255f,
            androidColor.green / 255f,
            androidColor.blue / 255f,
            androidColor.alpha / 255f
        )
    }
}
