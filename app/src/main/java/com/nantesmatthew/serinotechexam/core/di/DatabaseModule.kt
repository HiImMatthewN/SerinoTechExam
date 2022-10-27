package com.nantesmatthew.serinotechexam.core.di

import android.content.Context
import androidx.room.Room
import com.nantesmatthew.serinotechexam.core.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context):AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,"app_database").build()
    }


}