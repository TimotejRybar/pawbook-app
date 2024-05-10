package presentation.screen.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.enums.LoginState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.pawbook_logo
import presentation.components.loading.LoadingAnimation
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel = koinInject(), onLoginSucces: () -> Unit ) {
    val loginState by viewModel.state.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
                LogInForm() { username, password ->
                viewModel.login(username, password) {
                    onLoginSucces()
                }
            }
            LogInSocial()
            ForgotPassword()
            CreateAccount() {
                //createAccount...
            }
            Text("Don't Have an Account?", fontSize = 10.sp)
            Footer()
        }
        AnimatedContent(targetState = loginState) { targetCount ->
            if (targetCount == LoginState.LOADING) {
                LoadingAnimation()
            }

            val openAlertDialog = remember { mutableStateOf(true) }

            if (targetCount == LoginState.NO_INTERNET) {
                if(openAlertDialog.value) {
                    BasicAlertDialog(onDismissRequest = { openAlertDialog.value = false }) {
                        Card {
                            Text("Please check your internet connection", Modifier.padding(24.dp))
                        }
                    }
                }
            }

            val openErrorDialog = remember { mutableStateOf(true) }

            if(targetCount == LoginState.INVALID_LOGIN) {
                if(openErrorDialog.value) {
                    BasicAlertDialog(onDismissRequest = { openErrorDialog.value = false }) {
                        Card {
                            Text("Invalid e-mail or password", Modifier.padding(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LogInSocial() {
    StyledButton("Log in with Google") {
    }
}

@Composable
fun Footer() {
    Text("www.pawbook.com", fontSize = 10.sp)
}

@Composable
fun CreateAccount(onCreateClick: () -> Unit) {
    StyledButton("Create account", extraHorizontalPadding = 24.dp, onClick = onCreateClick)
}

enum class ButtonStyle{
    FillPrimary,
    FillSecondary,
    OutlinePrimary,
    OutlineSecondary
}
@Composable
fun StyledButton(text:String = "", buttonStyle: ButtonStyle = ButtonStyle.FillPrimary, extraHorizontalPadding: Dp = 0.dp, onClick: () -> Unit) {
    val backgroundColor = if(buttonStyle == ButtonStyle.FillPrimary) LocalAppColors.current.primary else LocalAppColors.current.secondary
    if(buttonStyle == ButtonStyle.OutlinePrimary || buttonStyle == ButtonStyle.OutlineSecondary) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.shadow(0.dp).fillMaxWidth().padding(64.dp, 0.dp),
            contentPadding = PaddingValues(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(50),
        ) {
            Text(text, fontSize = 10.sp)
        }
    } else {
        Button(
            onClick = onClick,
            modifier = Modifier.shadow(0.dp).fillMaxWidth().padding(64.dp, 0.dp),
            contentPadding = PaddingValues(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(50),
        ) {
            Text(text, fontSize = 10.sp)
        }
    }
}

@Composable
fun ForgotPassword() {
    Text("Forgot password", fontSize = 12.sp)
}

@Composable
fun LogInForm(onLoginSubmit: (username: String, password: String) -> Unit) {
    var email = remember { mutableStateOf("")}
    var password = remember { mutableStateOf("")}

    InputField("E-mail", email.value, InputType.EMAIL){
        email.value = it
    }
    Spacer(modifier = Modifier.height(10.dp))
    InputPasswordField("Password", password.value){
        password.value = it
    }
    Spacer(modifier = Modifier.height(20.dp))
    LogInButton() {
        onLoginSubmit(email.value, password.value)
    }
}

@Composable
fun LogInButton(onLoginSubmit: () -> Unit) {
    // call submit on click
    StyledButton(text = "Log in", extraHorizontalPadding = 0.dp, onClick = onLoginSubmit)
}

@Composable
fun InputPasswordField(title: String, value: String, onTextChange: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        label = {
            Text(title, color = Color.Black)
        },
        value = value,
        onValueChange = onTextChange,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor =  LocalAppColors.current.primary,
            unfocusedIndicatorColor = LocalAppColors.current.primary,
            ),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Lock
            else Icons.Filled.Lock

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }

    )
}

enum class InputType{
    EMAIL,
    PHONE
}


@Composable
fun InputField(title: String, value: String, type: InputType, onTextChange: (String) -> Unit) {

    var isValid by remember { mutableStateOf(false) }
    val keyboardType = when (type) {
        InputType.EMAIL -> KeyboardType.Text
        InputType.PHONE -> KeyboardType.Phone
    }
    TextField(
        label = {
            Text(title, color = Color.Black)
        },
        value = value,
        onValueChange = {
            isValid = validateField(type, it)
            onTextChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
        cursorColor = Color.Black,
        focusedIndicatorColor =  LocalAppColors.current.primary,
        unfocusedIndicatorColor = LocalAppColors.current.primary,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        errorIndicatorColor = LocalAppColors.current.error
        ),
        isError = !isValid,
    )
    if (!isValid) {
        Text(text = "Invalid value", color = Color.Red)
    }
}

fun validateField(type: InputType, input: String): Boolean {
    when(type) {
        InputType.EMAIL -> return input.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"))
        InputType.PHONE -> return input.matches(Regex("09(0|1)[5678][0-9][0-9][0-9][0-9][0-9][0-9]")) // TODO: this is for Slovakia, use translation resources in the future
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Logo(){
    Image(
        painter = painterResource(Res.drawable.pawbook_logo),
        contentDescription = "logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(300.dp,235.dp))
}
