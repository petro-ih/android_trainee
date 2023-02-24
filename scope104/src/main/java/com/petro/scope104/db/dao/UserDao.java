package com.petro.scope104.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petro.scope104.db.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * " + "FROM user u " + "JOIN country c on c.countryCode = u.nat")
    LiveData<List<UserBook>> loadUserAndBookNames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserEntity> user);
}

class UserBook {
    public String username;
    public String gender;
    public String country;
}
