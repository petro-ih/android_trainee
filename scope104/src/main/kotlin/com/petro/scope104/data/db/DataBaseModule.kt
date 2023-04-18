package com.petro.scope104.data.db

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.petro.scope104.data.UserDataSource
import com.petro.scope104.data.db.dao.CountryDao
import com.petro.scope104.data.db.dao.UserDao
import com.petro.scope104.data.qualifier.DataBaseQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context?): DataBase {
        return databaseBuilder(context!!, DataBase::class.java, "userDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providesUserDao(dataBase: DataBase): UserDao {
        return dataBase.userDao()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providesCountryDao(dataBase: DataBase): CountryDao {
        return dataBase.countryDao()
    }

    @JvmStatic
    @Provides
    @DataBaseQualifier
    @Singleton
    fun userDbDataSource(impl: UserDataSourceDbImpl): UserDataSource {
        return impl
    }
}