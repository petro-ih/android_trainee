package com.petro.scope104.data.db;

import android.content.Context;

import androidx.room.Room;

import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.db.dao.CountryDao;
import com.petro.scope104.data.db.dao.UserDao;
import com.petro.scope104.data.qualifier.DataBaseQualifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DataBaseModule {
    @Provides
    @Singleton
    public static DataBase provideDataBase(@ApplicationContext Context context) {
       return Room.databaseBuilder(context, DataBase.class, "userDB")
               .allowMainThreadQueries()
               .fallbackToDestructiveMigration()
               .build();
    }

    @Provides
    @Singleton
    public static UserDao providesUserDao(DataBase dataBase) {
        return dataBase.userDao();
    }

    @Provides
    @Singleton
    public static CountryDao providesCountryDao(DataBase dataBase) {
        return dataBase.countryDao();
    }

    @Provides
    @DataBaseQualifier
    @Singleton
    public static UserDataSource userDbDataSource(UserDataSourceDbImpl impl) {
        return impl;
    }
}