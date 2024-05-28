package data.local

import android.content.Context
import androidx.room.Room
import org.koin.core.context.GlobalContext

actual fun getDatabase(): AppDatabase {
    val context: Context = GlobalContext.get().get()
    val dbFile = context.getDatabasePath("pawbook.db")
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        dbFile.absolutePath)
        .build()
}