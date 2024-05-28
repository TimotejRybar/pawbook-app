package domain.model

interface Selectable {
    val id: String
    val name: String
    val key: String
    val updated: Long
    val created: Long
}