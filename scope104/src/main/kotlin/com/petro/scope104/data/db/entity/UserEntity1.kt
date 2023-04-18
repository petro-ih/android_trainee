package com.petro.scope104.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
class UserEntity(
    @field:PrimaryKey var username: String,
    @field:ColumnInfo var avatarUrl: String,
    @field:ColumnInfo var avatarUrlXXL: String,
    @field:ColumnInfo var firstName: String,
    @field:ColumnInfo var lastName: String,
    @field:ColumnInfo var dob: Date,
    @field:ColumnInfo var age: Int,
    @field:ColumnInfo var city: String,
    @field:ColumnInfo var country: String,
    @field:ColumnInfo var nat: String,
    @field:ColumnInfo var phone: String,
    @field:ColumnInfo var email: String,
    @field:ColumnInfo var registered: Date,
    @field:ColumnInfo var isMale: Boolean
)