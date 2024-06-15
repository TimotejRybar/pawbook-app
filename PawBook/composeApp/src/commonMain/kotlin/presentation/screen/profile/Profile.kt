package presentation.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import domain.model.result.User
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.cancel
import pawbook.composeapp.generated.resources.city
import pawbook.composeapp.generated.resources.confirm
import pawbook.composeapp.generated.resources.continue_pet_upload_profile_photo
import pawbook.composeapp.generated.resources.my_name
import pawbook.composeapp.generated.resources.select
import pawbook.composeapp.generated.resources.select_photo
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.screen.login.StyledButton
import presentation.screen.petCreate.CirclePhoto
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Profile(viewModel: ProfileViewModel = koinInject()){

    val userProfile by viewModel.profile.collectAsState()
    val city by remember { mutableStateOf("") }
    val userProfilePhotoUploaded by remember { mutableStateOf(viewModel.isUserProfilePhotoUploaded()) }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            UserPhoto(viewModel, userProfile, userProfilePhotoUploaded) {
            }
            InputField(
                stringResource(Res.string.my_name),
                userProfile?.name ?: "",
                InputType.TEXT
            ) {
                viewModel.updateProfileField(userProfile?.copy(name = it))
            }
            /* // TODO
            DatePropField( stringResource(Res.string.birthday),) {
                viewModel.updateProfileField(
                    userProfile?.copy(
                        birthDay = it.toInstant()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
            }*/
            InputField(stringResource(Res.string.city), city, InputType.TEXT, 1) {
                viewModel.updateProfileField(userProfile?.copy(city = it))
            }
            Spacer(modifier = Modifier.height(20.dp))
            StyledButton(text = stringResource(Res.string.confirm)) {
                viewModel.updateProfile()
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.loadProfile()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UserPhoto(viewModel: ProfileViewModel, userProfile: User?, userProfilePhotoUploaded: Boolean, onUploaded:(photoId: String) -> Unit) {
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

    CirclePhoto(currentPhotoFile.value) {
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
                    Text(stringResource(Res.string.cancel))
                }
            }
        )
    }
    if(userProfilePhotoUploaded) {
        CirclePhoto(imageData = currentPhotoFile.value) {
            // open photo picker
            openDialog.value = true
        }
    } else {
        CirclePhotoUploadPlaceholder() {
            // open photo picker
            openDialog.value = true
        }
    }
}

@Composable
fun CirclePhotoUploadPlaceholder(onClick: () -> Unit) {
    val primaryColor = LocalAppColors.current.primary
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .drawBehind {
                val paint = Paint().apply {
                    isAntiAlias = true
                    strokeWidth = 4f
                    color = primaryColor
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
                drawIntoCanvas {
                    it.drawCircle(
                        center = center,
                        radius = size.minDimension / 2 - paint.strokeWidth / 2,
                        paint = paint
                    )
                }
            }
    ) {
        Text(
            text = "Kliknite sem pre nahratie fotky",
            color = primaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}