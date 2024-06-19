package presentation.screen.galleryUpload

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerLauncher
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import domain.model.enums.MediaType
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.theme.colors.LocalAppColors

@Composable
fun GalleryUpload(viewModel: GalleryUploadViewModel = koinInject()) {

    val context = LocalPlatformContext.current
    val files = remember { mutableStateListOf<KmpFile>() }

    val localScope = rememberCoroutineScope()

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.ImageVideo,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = {
            localScope.launch {
                if (it.isEmpty()) {
                    // no files selected
                } else {
                    files.clear()
                    files.addAll(it)
                    viewModel.loadFiles(context, files)
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        GalleryUploadFiles(viewModel, pickerLauncher)
    }
}

@Composable
fun GalleryUploadFiles(viewModel: GalleryUploadViewModel, pickerLauncher: FilePickerLauncher) {
    val filesData by viewModel.selectedFiles.collectAsState()
    val videoThumbnails by viewModel.videoThumbnails.collectAsState()

    val primaryColor = LocalAppColors.current.primary
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Text("Vyberte súbory", textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
    Column(modifier = Modifier.fillMaxWidth().drawBehind {
        drawRoundRect(color = primaryColor, style = stroke)
    }.clickable {
        pickerLauncher.launch()
    }) {
        if (filesData.isEmpty()) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text("Kliknite sem pre výber súborov")
            }
        } else {
            filesData.forEach { file ->

                Row(modifier = Modifier.padding(16.dp)) {
                    when (file.mediaType) {

                        MediaType.Photo -> {
                            Image(
                                modifier = Modifier.size(64.dp, 64.dp),
                                painter = rememberAsyncImagePainter(model = file.data),
                                contentDescription = null,
                            )
                        }

                        MediaType.Video -> {
                            Image(
                                modifier = Modifier.size(64.dp, 64.dp),
                                painter = rememberAsyncImagePainter(model = videoThumbnails[file]),
                                contentDescription = null,
                            )
                        }

                        MediaType.Unknown -> {
                            Text(file.name)
                        }

                    }
                }
            }
        }
    }
}