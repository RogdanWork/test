package ua.roman.testtask.data.enitty

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BootEventEntity(@PrimaryKey val time: Long)