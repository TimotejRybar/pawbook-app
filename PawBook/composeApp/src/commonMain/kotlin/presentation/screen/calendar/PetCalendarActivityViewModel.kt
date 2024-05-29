package presentation.screen.calendar

import androidx.lifecycle.ViewModel
import data.model.entity.PetEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

class PetCalendarActivityViewModel: ViewModel(), KoinComponent {
    private val _pets = MutableStateFlow<ArrayList<PetEntity>>(arrayListOf())
    val pets: StateFlow<ArrayList<PetEntity>> = _pets
}