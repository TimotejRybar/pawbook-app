package utils.compose
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.AVFoundation.AVAssetImageGenerator
import platform.AVFoundation.AVURLAsset
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToFile
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

actual fun getThumbnailGenerator(): ThumbnailGenerator {
    return ThumbnailGenerator()
}

class ThumbnailGenerator() {
    @OptIn(ExperimentalForeignApi::class)
    suspend fun generateThumbnail(videoData: ByteArray): ByteArray? {
        return withContext(Dispatchers.Default) {
            try {
                // Create a temporary directory and file for the video
                val tempDir = NSTemporaryDirectory()
                val tempFile = "$tempDir/temp_video.mp4"
                videoData.usePinned { pinned ->
                    val nsData =
                        NSData.dataWithBytes(pinned.addressOf(0), videoData.size.toULong())
                    nsData.writeToFile(tempFile, true)
                }

                // Load the video file as an AVURLAsset
                val asset = AVURLAsset(NSURL.fileURLWithPath(tempFile), options = null)
                val imgGenerator = AVAssetImageGenerator(asset)
                imgGenerator.appliesPreferredTrackTransform = true

                // Generate a thumbnail at 1 second into the video
                val time = CMTimeMake(value = 1, timescale = 1)
                val cgImage = imgGenerator.copyCGImageAtTime(time, actualTime = null, error = null)
                val uiImage = UIImage(cgImage)

                // Convert the thumbnail to JPEG data
                val jpegData = UIImageJPEGRepresentation(uiImage, compressionQuality = 1.0)
                jpegData?.toByteArray()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun NSData.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
        }
    }
}