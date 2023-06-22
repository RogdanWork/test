package ua.roman.testtask.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.roman.testtask.data.enitty.BootEventEntity

@Dao
interface BootEventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: BootEventEntity)

    @Query("SELECT * FROM BootEventEntity ORDER BY time ASC")
    fun observeBootUpdates(): LiveData<List<BootEventEntity>>

    @Query("SELECT * FROM BootEventEntity ORDER BY time ASC")
    fun getOrderedByTimeEvents(): List<BootEventEntity>
}