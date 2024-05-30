package core.enums

import kotlinx.serialization.Serializable

@Serializable
enum class PetType(val value: String) {
    Dog("Dog"),
    Cat("Cat"),
    Rabbit("Rabbit"),
    GuineaPig("GuineaPing");
}