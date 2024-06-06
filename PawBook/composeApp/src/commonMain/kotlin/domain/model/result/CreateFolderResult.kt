package domain.model.result

import domain.model.StorageEntry
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderResult (val folder: StorageEntry)