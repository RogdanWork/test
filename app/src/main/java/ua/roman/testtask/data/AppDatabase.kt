package ua.roman.testtask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.roman.testtask.data.dao.BootEventsDao
import ua.roman.testtask.data.enitty.BootEventEntity

@Database(
    entities = [BootEventEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bootDao(): BootEventsDao

    companion object {
        const val DB_NAME = "test_db"
    }
}