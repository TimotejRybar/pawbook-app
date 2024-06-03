package presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.readByteArray
import core.util.Resources
import data.repository.ProfileRepositoryImpl
import domain.model.enums.ProfileViewState
import domain.model.result.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel: ViewModel(), KoinComponent {
    private val profileRepository: ProfileRepositoryImpl by inject()

    private val _state = MutableStateFlow(ProfileViewState.INIT)
    val state: StateFlow<ProfileViewState> = _state

    private val _profile = MutableStateFlow<User?>(null)
    val profile: StateFlow<User?> = _profile

    fun loadProfile() {
        viewModelScope.launch {
            val userProfile = profileRepository.fetchMyProfile()
            _profile.value = userProfile
        }
    }

    fun updateProfileField(updatedUser: User?) {
        viewModelScope.launch {
            _profile.value = updatedUser
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            profileRepository.updateProfile(_profile.value).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { ProfileViewState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { ProfileViewState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { ProfileViewState.LOADING }
                    }
                    is Resources.Success -> {
                        _state.update { ProfileViewState.SUCCESS }
                    }
                }
            }
        }
    }

    fun uploadProfilePicture(
        context: PlatformContext,
        profile: User?,
        files: List<KmpFile>,
        onUploaded: (result: String) -> Unit
    ) {
        viewModelScope.launch {
            profileRepository.uploadProfilePicture(profile, files[0].readByteArray(context)).collect {
                when(it) {
                    is Resources.Error -> {
                        if(it.message == "no_internet") _state.update { ProfileViewState.NO_INTERNET }
                        if(it.message == "internal_error") _state.update { ProfileViewState.ERROR }
                    }
                    is Resources.Loading -> {
                        _state.update { ProfileViewState.LOADING }
                    }
                    is Resources.Success -> {
                        onUploaded(profile?.id ?: "") // empty string should trigger removal of profile pic from server
                        _state.update { ProfileViewState.SUCCESS }
                    }
                }
            }
        }

    }
}