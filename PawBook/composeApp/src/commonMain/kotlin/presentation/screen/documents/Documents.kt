package presentation.screen.documents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.io.KmpFile
import org.koin.compose.koinInject
import presentation.screen.contact.ContactViewModel

@Composable
fun Documents(viewModel: ContactViewModel = koinInject()){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        FileManager()
    }
}

@Composable
fun FileManager() {

    val rootDir = remember { mutableListOf<KmpFile?>() }
    val files = remember { mutableListOf<KmpFile?>() }

    LazyColumn {
        items(files) {

        }
    }
}

enum class StorageEntryType {
    RETURN,
    FOLDER,
    PDF,
    ARCHIVE,
    PHOTO,
    DOCUMENT,
    UNKNOWN
}

data class StorageEntry(val storageEntryType: StorageEntryType, val name: String, val vPath: String, val storageKey: String)

@Composable
fun FileEntry(storageEntry: StorageEntry){
    // icon -> directory, pdf, archive, return

    when(storageEntry.storageEntryType){
        StorageEntryType.RETURN -> TODO()
        StorageEntryType.FOLDER -> TODO()
        StorageEntryType.PDF -> TODO()
        StorageEntryType.ARCHIVE -> TODO()
        StorageEntryType.PHOTO -> TODO()
        StorageEntryType.DOCUMENT -> TODO()
        StorageEntryType.UNKNOWN -> TODO()
    }

    Row(modifier = Modifier.clickable {

    }) {
        Text(storageEntry.name)
    }
}