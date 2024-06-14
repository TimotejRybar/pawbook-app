package utils.compose

import data.model.entity.PetEntity
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

    fun getPropFieldValue(petPropFieldType: PetPropFieldType, pet: PetEntity?): String {
        return when(petPropFieldType) {
            PetPropFieldType.GENDER -> pet?.gender as String
            PetPropFieldType.NAME -> pet?.name as String
            PetPropFieldType.BIRTH -> pet?.birthDay?.format().toString()
            PetPropFieldType.WEIGHT -> pet?.weight.toString()
            PetPropFieldType.BREED -> "Breed"
            else -> return ""
        }
    }
}