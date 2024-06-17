package presentation.screen.galleryUpload

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerLauncher
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import domain.model.enums.MediaType
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.theme.colors.LocalAppColors

@Composable
fun GalleryUpload(viewModel: GalleryUploadViewModel = koinInject()) {
    var files =  mutableStateListOf<KmpFile?>(null)

    val localScope = rememberCoroutineScope()

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = {
            localScope.launch {
                if (it.isEmpty()) {
                    // no files selected
                } else {
                    files.clear()
                    files.addAll(it)
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        GalleryUploadFiles(files, pickerLauncher)
    }
}

@Composable
fun GalleryUploadFiles(files: List<KmpFile?>, pickerLauncher: FilePickerLauncher) {
    val context = LocalPlatformContext.current
    val primaryColor = LocalAppColors.current.primary
    val stroke = Stroke(width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Text("Vyberte súbory", textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
    Column (modifier = Modifier.fillMaxWidth().drawBehind {
        drawRoundRect(color = primaryColor, style = stroke)
    }.clickable {
            pickerLauncher.launch()
        }) {
        if (files.isEmpty()) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text("Kliknite sem pre výber súborov")
            }
        } else {
            files.forEach { file ->

                val fileName = file?.getName(context)
                val fileExtension = fileName?.split(".")?.last()
                val mediaType = fileName?.split(".")?.get(1).let { ext ->
                    when (ext?.uppercase() as String) {
                        "JPG", "JPEG", "PNG", "RAW" -> MediaType.Photo
                        "MP4", "MPEG", "MKV" -> MediaType.Video
                        else -> MediaType.Unknown
                    }
                }
                Row(modifier = Modifier.padding(16.dp)) {
                    when (mediaType) {

                        MediaType.Photo -> {
                            KamelImage(
                                resource = asyncPainterResource(data = file?.readByteArray(context)),
                                contentDescription = null
                            )
                        }

                        MediaType.Video -> {
                            VideoThumbnail(file.uri)  // Assume you have a composable or function to generate a video thumbnail
                        }

                        MediaType.Unknown -> {
                            Text(file?.getName(context).toString())

                        }
                    }
                }
            }
        }
    }
}
