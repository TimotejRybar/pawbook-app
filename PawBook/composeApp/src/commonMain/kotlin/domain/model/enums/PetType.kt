package domain.model.enums

import kotlinx.serialization.Serializable

enum class PetType(val value: String) {
    Dog("Dog"),
    Cat("Cat"),
    Rabbit("Rabbit"),
    GuineaPig("GuineaPing");
}