package presentation.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.window.DialogProperties
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import domain.model.result.User
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.birthday
import pawbook.composeapp.generated.resources.city
import pawbook.composeapp.generated.resources.confirm
import pawbook.composeapp.generated.resources.continue_pet_upload_profile_photo
import pawbook.composeapp.generated.resources.my_name
import pawbook.composeapp.generated.resources.select
import pawbook.composeapp.generated.resources.select_photo
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.screen.login.StyledButton
import presentation.screen.petDetail.CirclePhoto
import presentation.screen.petDetail.DatePropField
import presentation.theme.colors.LocalAppColors
import utils.compose.toInstant

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Profile(viewModel: ProfileViewModel = koinInject()){

    val userProfile by viewModel.profile.collectAsState()
    val city by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row {
            Column {
                UserPhoto(viewModel, userProfile){
                }
                InputField(stringResource(Res.string.my_name), userProfile?.name ?: "", InputType.TEXT) {
                    viewModel.updateProfileField(userProfile?.copy(name = it ))
                }
                DatePropField(stringResource(Res.string.birthday), ){
                    viewModel.updateProfileField(userProfile?.copy(birthDay = it.toInstant().toLocalDateTime(TimeZone.currentSystemDefault())))
                }
                InputField(stringResource(Res.string.city), city, InputType.TEXT, 1){
                    viewModel.updateProfileField(userProfile?.copy(city = it ))
                }
                StyledButton(text = stringResource(Res.string.confirm)) {
                    viewModel.updateProfile()
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.loadProfile()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UserPhoto(viewModel: ProfileViewModel, userProfile: User?, onUploaded:(photoId: String) -> Unit) {
    val openDialog = remember { mutableStateOf(false) }
    val currentPhoto = remember { mutableStateOf("") }
    val currentPhotoFile = remember { mutableStateOf<ByteArray?>(null) }
    val localScope = rememberCoroutineScope()

    val context = LocalPlatformContext.current
    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            viewModel.uploadProfilePicture(context, userProfile, files)  {
                localScope.launch {
                    currentPhoto.value = "local"
                    currentPhotoFile.value = files[0].readByteArray(context)
                    onUploaded(userProfile?.id ?: "")
                }
            }
        })

    CirclePhoto(currentPhotoFile.value, currentPhoto.value) {
        openDialog.value = true
    }
    if (openDialog.value) {
        AlertDialog(
            containerColor = LocalAppColors.current.secondary,
            onDismissRequest = {},
            properties = DialogProperties(),
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalAppColors.current.primary
                    ),
                    onClick = {
                        pickerLauncher.launch()
                    }) {
                    Text(color = Color.White, text = stringResource(Res.string.select))
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Star, "dialog icon")
            },
            title = { Text(stringResource(Res.string.select_photo)) },
            text = { Text(stringResource(Res.string.continue_pet_upload_profile_photo)) },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalAppColors.current.darkGray
                    ),
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Zrušiť")
                }
            }
        )
    }
    CirclePhoto(imageUrl =  userProfile?.id ?: "", imageData = currentPhotoFile.value) {
        // open photo picker
        openDialog.value = true
    }
}