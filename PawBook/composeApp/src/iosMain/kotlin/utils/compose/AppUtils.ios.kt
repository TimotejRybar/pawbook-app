package utils.compose

import platform.posix.exit

actual fun closeApp() {
    exit(0)
}