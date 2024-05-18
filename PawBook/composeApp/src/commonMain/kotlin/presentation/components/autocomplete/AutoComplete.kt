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
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import core.util.stringByKey.StringByKey
import core.util.stringByKey.StringType
import domain.Selectable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun AutoComplete(
    label: String,
    hint: String,
    options: List<Selectable>,
    onItemSelected: (Selectable) -> Unit
) {
    var selectedValue by remember { mutableStateOf<Selectable?>(null) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding().padding(horizontal = 32.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                modifier = Modifier
                    .weight(1f)
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
                value = inputValue,
                isError = inputValue.isNotEmpty() && options.none {
                    stringResource(
                        StringByKey.getStringValue(
                            StringType.BREED,
                            it.key
                        )
                    ).contains(inputValue, ignoreCase = true)
                },
                onValueChange = {
                    inputValue = it
                    selectedValue =
                        options.find { option -> option.name.equals(it, ignoreCase = true) }
                    expanded = it.isNotEmpty()
                },
                placeholder = { Text(hint) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = LocalAppColors.current.primary,
                    unfocusedIndicatorColor = LocalAppColors.current.primary,
                    errorContainerColor = Color.Transparent,
                    errorIndicatorColor = LocalAppColors.current.error,
                    errorTextColor = LocalAppColors.current.error
                ),
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
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 150.dp)
                        .background(LocalAppColors.current.primary)
                ) {
                    val filteredOptions = options
                        .filter { it.name.contains(inputValue, ignoreCase = true) }
                    items(filteredOptions) { item ->
                        ItemCategory(selectable = item) { selectedTitle ->
                            selectedValue = selectedTitle
                            inputValue = selectedTitle.name
                            expanded = false
                            onItemSelected(selectedTitle)
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemCategory(
    selectable: Selectable,
    onSelect: (Selectable) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(selectable) }
            .padding(30.dp, 10.dp)
    ) {
        Text(text = stringResource(StringByKey.getStringValue(StringType.BREED, selectable.key)), fontSize = 16.sp)
    }
}
