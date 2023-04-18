package com.petro.scope104.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.petro.scope104.data.db.dao.CountryDao
import com.petro.scope104.data.db.dao.UserDao
import com.petro.scope104.data.db.entity.CountryEntity
import com.petro.scope104.data.db.entity.UserEntity

@Database(entities = [UserEntity::class, CountryEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
}