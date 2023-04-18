package com.petro.scope104.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
class CountryEntity(
    @field:PrimaryKey
    var countryCode: String,
    @field:ColumnInfo(name = "name")
    var name: String
)