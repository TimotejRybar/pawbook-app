package presentation.screen.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
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
import domain.model.enums.StorageState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.back
import pawbook.composeapp.generated.resources.cancel
import pawbook.composeapp.generated.resources.confirm
import pawbook.composeapp.generated.resources.directory_name
import pawbook.composeapp.generated.resources.new_folder
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Storage(storageState: StorageState, viewModel: StorageViewModel = koinInject()) {

    val localScope = rememberCoroutineScope()
    val currentPath = viewModel.currentPath.collectAsState()
    val openCreateFolderDialog = remember { mutableStateOf(false) }
    val backString = stringResource(Res.string.back)

    LaunchedEffect(true) {
        viewModel.fetchStorage(currentPath.value, backString)
    }

    Row (
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FileManager(viewModel)
    }

    val context = LocalPlatformContext.current
    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            localScope.launch {
                if(files.isEmpty()) {
                    // no files selected
                } else {
                    viewModel.uploadFile(context, files[0], currentPath.value)
                }
            }
        })

    if(openCreateFolderDialog.value) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(),
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.secondary,
                ),
                shape = RoundedCornerShape(20.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val newFolderName = remember { mutableStateOf("") }
                    Text(stringResource(Res.string.new_folder), fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    InputField(stringResource(Res.string.directory_name), newFolderName.value, InputType.TEXT) {
                        newFolderName.value = it
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    Row (horizontalArrangement =  Arrangement.Center) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LocalAppColors.current.primary
                            ),
                            onClick = {
                                viewModel.createFolder(newFolderName.value, currentPath.value)
                            }) {
                            Text(color = Color.White, text = stringResource(Res.string.confirm))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LocalAppColors.current.darkGray
                            ),
                            onClick = {
                                openCreateFolderDialog.value = false
                            }) {
                            Text(stringResource(Res.string.cancel))
                        }
                    }
                }
            }
        }
    }

    if(storageState === StorageState.CREATE_FOLDER) {
        LaunchedEffect(true) {
            openCreateFolderDialog.value = true
        }
    }

    if(storageState === StorageState.UPLOAD_FILE) {
        LaunchedEffect(storageState === StorageState.UPLOAD_FILE) {
            pickerLauncher.launch()
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FileManager(viewModel: StorageViewModel) {
    val files by viewModel.allFiles.collectAsState()
    val backString = stringResource(Res.string.back)

    LazyColumn(modifier = Modifier.heightIn(150.dp, 300.dp)) {
        if (files.isEmpty()) {
            item {
                Text("This folder is empty.", modifier = Modifier.padding(16.dp))
            }
        } else {
            items(files) { storageEntry ->
                FileEntry(storageEntry) { storageEntryEntity ->
                    var newPath: String? = null
                    if (storageEntryEntity.storageEntryType == StorageEntryType.FOLDER) {
                        newPath = storageEntryEntity.vPath + storageEntryEntity.name + "/"
                        viewModel.setCurrentPath(newPath)
                        viewModel.fetchStorage(newPath, backString)
                    }
                    else if (storageEntryEntity.storageEntryType == StorageEntryType.RETURN) {
                        newPath = removeLastFolder(storageEntryEntity.vPath)
                    }

                    newPath.let {
                        viewModel.setCurrentPath(it as String)
                        viewModel.fetchStorage(it, backString)
                    }
                }
            }
        }
    }
}

fun removeLastFolder(path: String): String {
    val regex = Regex("[^/]+/$")
    val match = regex.find(path)
    return if (match != null) {
        val startIndex = path.lastIndexOf(match.value)
        path.substring(0, startIndex)
    } else {
        path
    }
}

@Composable
fun FileEntry(storageEntry: StorageEntryEntity, onClick: (StorageEntryEntity) -> Unit) {
    val icon: ImageVector = iconFromStorageEntryType(storageEntry.storageEntryType)
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp, 2.dp)
            .clickable { onClick(storageEntry) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(icon, modifier = Modifier.size(32.dp), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
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
