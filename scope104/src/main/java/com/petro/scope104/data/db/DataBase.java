package com.petro.scope104.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.petro.scope104.data.db.dao.CountryDao;
import com.petro.scope104.data.db.dao.UserDao;
import com.petro.scope104.data.db.entity.CountryEntity;
import com.petro.scope104.data.db.entity.UserEntity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Database(entities = {UserEntity.class, CountryEntity.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class DataBase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract CountryDao countryDao();
}

