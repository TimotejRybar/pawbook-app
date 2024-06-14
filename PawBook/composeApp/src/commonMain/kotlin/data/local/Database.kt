package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import core.enums.PetType
import data.model.entity.BreedEntity
import data.model.entity.CalendarActivityEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import data.model.entity.PetPhotoEntity
import data.model.entity.StorageEntryEntity
import domain.model.Address
import domain.model.EpilepsyRecord
import domain.model.Location
import domain.model.WeightRecord
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic


expect fun getDatabase(): AppDatabase

@TypeConverters(value = [Converters::class])
@Database(entities = [BreedEntity::class, ColorEntity::class,
    DoctorEntity::class, CalendarActivityEntity::class,
    PetEntity::class, PetPhotoEntity::class, StorageEntryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDoctorDao(): DoctorDao
    abstract fun getBreedDao(): BreedDao
    abstract fun getColorDao(): ColorDao
    abstract fun getCalendarDao(): CalendarActivityDao
    abstract fun getPetDao(): PetDao
    abstract fun getPetPhotoDao(): PetPhotoDao
    abstract fun getStorageDao(): StorageDao
   // abstract fun getConversationDao(): ConversationDao
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

    @JvmStatic
    @TypeConverter
    fun fromStringList(stringList: ArrayList<String>): String = Json.encodeToString(stringList)

    @JvmStatic
    @TypeConverter
    fun toStringList(value: String): ArrayList<String> = Json.decodeFromString(value)

    @JvmStatic
    @TypeConverter
    fun fromWeightHistory(weightHistory: ArrayList<WeightRecord>): String = Json.encodeToString(weightHistory)

    @JvmStatic
    @TypeConverter
    fun toWeightHistory(value: String): ArrayList<WeightRecord> = Json.decodeFromString(value)

    @JvmStatic
    @TypeConverter
    fun fromEpilepsyHistory(epilepsyHistory: ArrayList<EpilepsyRecord>): String = Json.encodeToString(epilepsyHistory)

    @JvmStatic
    @TypeConverter
    fun toEpilepsyHistory(value: String): ArrayList<EpilepsyRecord> = Json.decodeFromString(value)


    /*
        @JvmStatic
        @TypeConverter
        fun fromUserList(stringList: List<User>): String = Json.encodeToString(stringList)

        @JvmStatic
        @TypeConverter
        fun toUserList(value: String): List<User> = Json.decodeFromString(value)


        @JvmStatic
        @TypeConverter
        fun fromMessageList(stringList: List<User>): String = Json.encodeToString(stringList)

        @JvmStatic
        @TypeConverter
        fun toMessageList(value: String): List<Message> = Json.decodeFromString(value)
    */

    @JvmStatic
    @TypeConverter
    fun toDate(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @JvmStatic
    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }
}