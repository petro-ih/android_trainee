package com.petro.scope104.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.petro.scope104.db.dao.CountryDao;
import com.petro.scope104.db.dao.UserDao;
import com.petro.scope104.db.entity.CountryEntity;
import com.petro.scope104.db.entity.UserEntity;

@Database(entities = {UserEntity.class, CountryEntity.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class DataBase extends RoomDatabase {
    private static DataBase dataBase;

    public static DataBase getDataBase(Context context) {
        if (dataBase == null) {
            dataBase = Room
                    .databaseBuilder(context.getApplicationContext(), DataBase.class, "userDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBase;
    }

    public abstract UserDao userDao();
    public abstract CountryDao countryDao();
}