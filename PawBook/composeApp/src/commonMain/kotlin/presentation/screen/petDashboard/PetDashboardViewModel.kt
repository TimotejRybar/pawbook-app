package presentation.screen.petDashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import data.repository.PetDashboardRepositoryImpl
import data.repository.PetDetailRepositoryImpl
import domain.model.PetItem
import domain.model.enums.PetDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDashboardViewModel() : ViewModel(), KoinComponent {
    private val petDashboardRepository: PetDashboardRepositoryImpl by inject()

    private val _state = MutableStateFlow(PetDetailState.INIT)
    val state: StateFlow<PetDetailState> = _state

    val pet = mutableStateOf(PetItem.empty())

    private val _profilePicture = MutableStateFlow<String>("")
    val profilePicture: StateFlow<String> = _profilePicture
}
