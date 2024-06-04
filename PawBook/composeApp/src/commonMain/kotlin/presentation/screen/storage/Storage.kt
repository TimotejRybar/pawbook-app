package presentation.screen.storage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.ArrowAltCircleLeft
import compose.icons.fontawesomeicons.regular.File
import compose.icons.fontawesomeicons.regular.FileArchive
import compose.icons.fontawesomeicons.regular.FileImage
import compose.icons.fontawesomeicons.regular.FilePdf
import compose.icons.fontawesomeicons.regular.FileVideo
import compose.icons.fontawesomeicons.regular.FileWord
import compose.icons.fontawesomeicons.regular.Folder
import data.model.entity.StorageEntryEntity
import domain.model.enums.StorageEntryType
import org.koin.compose.koinInject

@Composable
fun Storage(viewModel: StorageViewModel = koinInject()){

    val allFiles = viewModel.allFiles.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchStorage()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        FileManager(allFiles.value) {

        }
    }
}

@Composable
fun FileManager(allFiles: List<StorageEntryEntity>, onClick: (StorageEntryEntity) -> Unit) {

    val currentPath = mutableStateOf(StorageViewModel.DEFAULT_DIRECTORY)
    val currentDirectory = remember { mutableListOf<StorageEntryEntity>() }

    LaunchedEffect(true) {
        currentDirectory.clear()
        currentDirectory.addAll(allFiles.filter { it.vPath == currentPath.value })
    }

    LazyColumn {
        items(currentDirectory) { storageEntry ->
            FileEntry(storageEntry) {
                onClick(it)
            }
        }
    }
}

@Composable
fun FileEntry(storageEntry: StorageEntryEntity, onClick: (StorageEntryEntity) -> Unit) {
    val icon: ImageVector = iconFromStorageEntryType(storageEntry.storageEntryType)

    Row(modifier = Modifier.clickable {
        onClick(storageEntry)
    }) {
        Icon(icon, "")
        Text(storageEntry.name)
    }
}

fun iconFromStorageEntryType(storageEntryType: StorageEntryType): ImageVector {
    return when (storageEntryType) {
        StorageEntryType.RETURN -> FontAwesomeIcons.Regular.ArrowAltCircleLeft
        StorageEntryType.FOLDER -> FontAwesomeIcons.Regular.Folder
        StorageEntryType.PDF -> FontAwesomeIcons.Regular.FilePdf
        StorageEntryType.ARCHIVE -> FontAwesomeIcons.Regular.FileArchive
        StorageEntryType.PHOTO -> FontAwesomeIcons.Regular.FileImage
        StorageEntryType.DOCUMENT -> FontAwesomeIcons.Regular.FileWord
        StorageEntryType.UNKNOWN -> FontAwesomeIcons.Regular.File
        StorageEntryType.VIDEO -> FontAwesomeIcons.Regular.FileVideo
    }
}