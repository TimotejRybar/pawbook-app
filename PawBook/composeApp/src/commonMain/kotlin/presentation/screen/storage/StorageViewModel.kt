package presentation.screen.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.entity.StorageEntryEntity
import data.repository.StorageRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageViewModel(): ViewModel(), KoinComponent {
    private val storageRepository: StorageRepositoryImpl by inject()

    private val _allFiles = MutableStateFlow<ArrayList<StorageEntryEntity>>(arrayListOf())
    val allFiles: StateFlow<ArrayList<StorageEntryEntity>> = _allFiles

    fun fetchStorage(){
        viewModelScope.launch {
            storageRepository.fetch().collect {
                _allFiles.value.clear()
                _allFiles.value.addAll(it)
            }
        }
    }
    companion object {
        val DEFAULT_DIRECTORY = "/root/"
    }
}