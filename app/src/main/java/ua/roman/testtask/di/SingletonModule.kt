package ua.roman.testtask.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.roman.testtask.data.AppDatabase
import ua.roman.testtask.tools.BootNotificationsTool
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, AppDatabase.DB_NAME
    ).fallbackToDestructiveMigration() // todo create DB migration if required
        .build()

    @Provides
    @Singleton
    fun provideBootEventsDao(database: AppDatabase) = database.bootDao()

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideNotificationTool(@ApplicationContext context: Context) = BootNotificationsTool(context)
}