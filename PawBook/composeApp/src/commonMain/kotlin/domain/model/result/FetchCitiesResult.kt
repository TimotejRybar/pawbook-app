package domain.model.result

import domain.model.City
import kotlinx.serialization.Serializable

@Serializable
data class FetchCitiesResult(val cities: List<City>)