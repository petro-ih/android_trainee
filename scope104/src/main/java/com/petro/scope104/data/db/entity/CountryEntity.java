package com.petro.scope104.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "country")
public class CountryEntity {
    @PrimaryKey
    @NonNull
    public String countryCode;

    @ColumnInfo(name = "name")
    public String name;

    public CountryEntity(@NonNull String countryCode, String name) {
        this.countryCode = countryCode;
        this.name = name;
    }
}
