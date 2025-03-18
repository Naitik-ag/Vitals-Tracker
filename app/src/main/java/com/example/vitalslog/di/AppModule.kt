package com.example.vitalslog.di

import android.content.Context
import androidx.room.Room
import com.example.vitalslog.data.database.VitalsDao
import com.example.vitalslog.data.database.VitalsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VitalsDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            VitalsDatabase::class.java,
            "vitals_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideVitalsDao(database: VitalsDatabase): VitalsDao {
        return database.vitalsDao()
    }

}