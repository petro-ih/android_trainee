package com.petro.scope104.data.db;

import com.petro.scope104.data.db.entity.CountryEntity;
import com.petro.scope104.data.db.entity.UserEntity;
import com.petro.scope104.domain.entity.WorkerEntity;

import java.util.Locale;

import javax.inject.Inject;

public class DbConverter {
    @Inject
    public DbConverter(){

    }
    public UserEntity mapUiToDatabase(WorkerEntity workerEntity) {
        return new UserEntity(workerEntity.getUsername(), workerEntity.getAvatarUrl(), workerEntity.getAvatarUrlXXL(), workerEntity.getFirstName(), workerEntity.getLastName(), workerEntity.getDob(), workerEntity.getAge(), workerEntity.getCity(), workerEntity.getCountry(), workerEntity.getNat(), workerEntity.getPhone(), workerEntity.getEmail(), workerEntity.getRegistered(), workerEntity.isMale());
    }

    public CountryEntity mapUiToCountryEntity(String countryCode) {
        Locale loc = new Locale("", countryCode);
        return new CountryEntity(countryCode, loc.getDisplayCountry());
    }
    public WorkerEntity mapDatabaseToUi(UserEntity userEntity) {
        return new WorkerEntity(userEntity.avatarUrl, userEntity.avatarUrlXXL, userEntity.firstName, userEntity.lastName, userEntity.dob, userEntity.age, userEntity.city, userEntity.country, userEntity.phone, userEntity.username, userEntity.email, userEntity.nat, userEntity.registered, userEntity.isMale);
    }
}
