package data.local

import androidx.room.Room
import androidx.sqlite.driver.NativeSQLiteDriver
import data.local.AppDatabase
import platform.Foundation.NSHomeDirectory


actual fun getDatabase(): AppDatabase {
    val dbFilePath = NSHomeDirectory() + "/pawbook.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() },
    ).setDriver(NativeSQLiteDriver())
        .build()
}
