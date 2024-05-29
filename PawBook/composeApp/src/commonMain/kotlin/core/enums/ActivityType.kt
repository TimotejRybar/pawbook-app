package core.enums

import kotlinx.serialization.Serializable

enum class ActivityType(val value: String) {
    Walking("Walking"),
    VisitVet("VisitVet"),
    NailTrimming("NailTrimming"),
    HairTrimming("HairTrimming");
}