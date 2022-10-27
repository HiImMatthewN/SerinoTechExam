package com.nantesmatthew.serinotechexam.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductDao
import com.nantesmatthew.serinotechexam.products.data.data_source.RemoteKeysDao
import com.nantesmatthew.serinotechexam.products.data.entities.ProductLocalEntity
import com.nantesmatthew.serinotechexam.products.data.entities.RemoteKeys

@Database(entities = [ProductLocalEntity::class,RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun productDao():ProductDao
    abstract fun remoteKeysDao():RemoteKeysDao
}