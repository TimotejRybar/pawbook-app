package presentation.theme.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class ThemeColors(
    val primary: Color,
    val secondary: Color,
    val error: Color,
    val darkGray: Color
)

val colorBackground = Color(0xFFF5F4F0)
val colorPrimary = Color(0xffdcbaae)
var colorError = Color(0xffff474c)
var colorDarkGray = Color(0xff3b3b3b)

val LightThemeAppColors = ThemeColors(
    primary = colorPrimary,
    secondary = colorBackground,
    error = colorError,
    darkGray = colorDarkGray
)

val LocalAppColors = staticCompositionLocalOf {
    LightThemeAppColors
}


