package core.util

import domain.model.PetItem

object PetUtils {
    fun getTestPet(): PetItem {
        return PetItem("Sofinka", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sollicitudin ut ex sollicitudin sollicitudin")
    }
}