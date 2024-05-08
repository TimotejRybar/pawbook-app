package utils.compose

import domain.model.enums.PetPropFieldType

object PetPropFieldUtils {
    fun getPropFieldText(petPropFieldType: PetPropFieldType): String {
        return when(petPropFieldType) {
            PetPropFieldType.GENDER -> "Gender"
            PetPropFieldType.NAME -> "Pet name"
            PetPropFieldType.BIRTH -> "Day of birth"
            PetPropFieldType.WEIGHT -> "Weight"
            PetPropFieldType.COLOR -> "Color"
            PetPropFieldType.BREED -> "Breed"
        }
    }
}