package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import core.common.Config
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import domain.model.Address
import domain.model.Location
import domain.model.enums.PetType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic

@TypeConverters(value = [Converters::class])
@Database(entities = [BreedEntity::class, ColorEntity::class, DoctorEntity::class], version = 0)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDoctorDao(): DoctorDao
    abstract fun getBreedDao(): BreedDao
    abstract fun getColorDao(): ColorDao
}


object Converters {
    @JvmStatic
    @TypeConverter
    fun fromAddress(address: Address): String = Json.encodeToString(address)

    @JvmStatic
    @TypeConverter
    fun toAddress(value: String): Address = Json.decodeFromString(value)

    @JvmStatic
    @TypeConverter
    fun fromLocation(location: Location): String = Json.encodeToString(location)

    @JvmStatic
    @TypeConverter
    fun toLocation(value: String): Location = Json.decodeFromString(value)

    @JvmStatic
    @TypeConverter
    fun fromSkills(skills: List<PetType>): String = Json.encodeToString(skills)

    @JvmStatic
    @TypeConverter
    fun toSkills(value: String): List<PetType> = Json.decodeFromString(value)
}
