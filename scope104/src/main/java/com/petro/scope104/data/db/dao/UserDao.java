package com.petro.scope104.data.db.dao;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petro.scope104.data.db.entity.UserEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Dao
public interface UserDao {
    @Query(
            "SELECT * " +
            "FROM user u " +
            "JOIN country c on c.countryCode = u.nat " +
            "WHERE (:isMale is NULL OR isMale = :isMale) AND (u.nat in (:nationality))" +
            "LIMIT :limit " +
            "OFFSET (:page * :limit) "
    )
    LiveData<List<UserEntity>> loadUsers(int page, int limit, @Nullable Boolean isMale, @NotNull List<String> nationality);

    @Query(
            "SELECT * " +
                    "FROM user u " +
                    "JOIN country c on c.countryCode = u.nat " +
                    "WHERE (:isMale is NULL OR isMale = :isMale)" +
                    "LIMIT :limit " +
                    "OFFSET (:page * :limit) "
    )
    LiveData<List<UserEntity>> loadUsers(int page, int limit, @Nullable Boolean isMale);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserEntity> user);
}

