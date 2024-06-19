package utils.compose

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.core.context.GlobalContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

actual fun getThumbnailGenerator(): ThumbnailGenerator {
    return ThumbnailGenerator()
}

actual class ThumbnailGenerator actual constructor() {
    actual suspend fun generateThumbnails(videoFiles: List<ByteArray>): Flow<Array<ByteArray?>> = flow {
        val context: Context = GlobalContext.get().get()
        val results = mutableListOf<ByteArray?>()

        videoFiles.forEach { file ->
            try {
                val result = withContext(Dispatchers.IO) {
                    val tempFile = File.createTempFile("temp_video", "mp4", context.cacheDir)
                    FileOutputStream(tempFile).use { it.write(file) }

                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(tempFile.absolutePath)
                    val bitmap = retriever.getFrameAtTime(3000000) // 3 seconds in microseconds
                    val stream = ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val byteArray = stream.toByteArray()
                    retriever.release()
                    tempFile.delete()

                    byteArray
                }
                results.add(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        emit(results.toTypedArray())

    }
}
