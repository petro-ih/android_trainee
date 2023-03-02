//package com.petro.scope104.db.entity;
//
//import androidx.room.Embedded;
//import androidx.room.Relation;
//
//import java.util.List;
//
//public class UserWithCountryAndGender {
//    @Embedded
//    public UserEntity user;
//    @Relation(
//            parentColumn = "username",
//            entityColumn = "id"
//    )
//    public List<GenderEntity> genderEntities;
//    @Relation(
//            parentColumn = "username",
//            entityColumn = "id"
//    )
//    public List<CountryEntity> countryEntities;
//}
