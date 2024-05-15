package presentation.components.autocomplete

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoComplete(
    label: String,
    options: List<String>,
    onItemSelected: (String) -> Unit
) {
    var selectedValue by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding()
            .clickable(onClick = { expanded = false })
    ) {

        Column() {
            Row() {
                TextField(
                    modifier = Modifier
                        .height(55.dp)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    label = {
                        Text(
                            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                            text = label,
                            fontSize = 16.sp,
                            color = Color.Black,
                        )
                    },
                    value = selectedValue,
                    isError = options.indexOf(selectedValue) == -1,
                    onValueChange = {
                        selectedValue = it
                        expanded = true
                    },
                    placeholder = { Text("Enter any Animals Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor =  LocalAppColors.current.primary,
                        unfocusedIndicatorColor = LocalAppColors.current.primary,
                        errorContainerColor = Color.Transparent,
                        errorIndicatorColor = LocalAppColors.current.error,
                        errorTextColor = LocalAppColors.current.error),
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier.padding(horizontal = 5.dp),
                       // .width(textFieldSize.width.dp),
                        shape = RoundedCornerShape(10.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .background(LocalAppColors.current.primary)
                    ) {
                        val filteredOptions = options
                            .filter { it.contains(selectedValue, ignoreCase = true) }
                            .sorted()

                        items(filteredOptions) { item ->
                            ItemCategory(title = item) { selectedTitle ->
                                selectedValue = selectedTitle
                                expanded = false
                                onItemSelected(selectedTitle)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCategory(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(title) }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}
