package com.petro.scope104.data.db.dao

import androidx.room.*
import com.petro.scope104.data.db.entity.UserEntity
import io.reactivex.rxjava3.core.Observable

@Dao
interface UserDao {
    @Query(
        "SELECT * " +
                "FROM user u " +
                "JOIN country c on c.countryCode = u.nat " +
                "WHERE (:isMale is NULL OR isMale = :isMale) AND (u.nat in (:nationality))" +
                "LIMIT :limit " +
                "OFFSET (:page * :limit) "
    )
    fun loadUsers(
        page: Int,
        limit: Int,
        isMale: Boolean?,
        nationality: List<String?>
    ): Observable<List<UserEntity>>

    @Query(
        "SELECT * " +
                "FROM user u " +
                "JOIN country c on c.countryCode = u.nat " +
                "WHERE (:isMale is NULL OR isMale = :isMale)" +
                "LIMIT :limit " +
                "OFFSET (:page * :limit) "
    )
    fun loadUsers(page: Int, limit: Int, isMale: Boolean?): Observable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<UserEntity?>?)
}