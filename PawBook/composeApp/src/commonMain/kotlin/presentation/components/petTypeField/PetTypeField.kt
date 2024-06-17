package presentation.components.petTypeField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cat
import compose.icons.fontawesomeicons.solid.Dog
import compose.icons.fontawesomeicons.solid.Horse
import compose.icons.fontawesomeicons.solid.Snowman
import core.enums.PetType
import presentation.theme.colors.LocalAppColors

@Composable
fun PetTypeField(onPetTypeSelected: (PetType) -> Unit) {
    var selectedPetType by remember { mutableStateOf<PetType?>(null) }

    Column {
        Row {
            PetType.entries.forEach { petType ->
                PetTypeItem(
                    petType = petType,
                    isSelected = petType == selectedPetType,
                    onClick = {
                        selectedPetType = it
                        onPetTypeSelected(it)
                    }
                )
            }
        }
    }
}

@Composable
fun PetTypeItem(petType: PetType, isSelected: Boolean, onClick: (PetType) -> Unit) {
    val colorTint = if (isSelected) LocalAppColors.current.primary else LocalAppColors.current.darkGray
    val petTypeIcon: ImageVector =
        when (petType) {
            PetType.Dog -> FontAwesomeIcons.Solid.Dog
            PetType.Cat -> FontAwesomeIcons.Solid.Cat
            PetType.Rabbit -> FontAwesomeIcons.Solid.Horse
            PetType.GuineaPig -> FontAwesomeIcons.Solid.Snowman
        }

    IconButton(onClick = { onClick(petType) }) {
        Icon(petTypeIcon, "pet type", tint = colorTint)
    }
    Spacer(modifier = Modifier.width(16.dp))
}