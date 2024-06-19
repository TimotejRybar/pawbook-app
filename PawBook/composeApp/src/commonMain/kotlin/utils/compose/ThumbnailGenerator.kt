package utils.compose

import kotlinx.coroutines.flow.Flow

expect fun getThumbnailGenerator(): ThumbnailGenerator

expect class ThumbnailGenerator () {
    suspend fun generateThumbnails(videoFiles: List<ByteArray>): Flow<Array<ByteArray?>>
}