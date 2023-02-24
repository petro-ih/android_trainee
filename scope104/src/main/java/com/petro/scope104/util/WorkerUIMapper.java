package com.petro.scope104.util;

import com.petro.scope104.db.entity.CountryEntity;
import com.petro.scope104.db.entity.UserEntity;
import com.petro.scope104.network.response.UserResponse;
import com.petro.scope104.ui.WorkerUi;

import java.util.Locale;

public class WorkerUIMapper {
    public static WorkerUi mapNetworkToUi(UserResponse user) {
        return new WorkerUi(user.picture.medium, user.picture.large, user.name.first, user.name.last, user.dob.date, user.dob.age, user.location.city, user.location.country, user.phone, user.login.username, user.email, user.nat, user.registered.date, user.gender.equals("male"));
    }

    public static UserEntity mapUiToDatabase(WorkerUi workerUi) {
        return new UserEntity(workerUi.getUsername(), workerUi.getAvatarUrl(), workerUi.getAvatarUrlXXL(), workerUi.getFirstName(), workerUi.getLastName(), workerUi.getDob(), workerUi.getAge(), workerUi.getNat(), workerUi.getPhone(), workerUi.getEmail(), workerUi.getRegistered(), workerUi.isMale());
    }

    public static CountryEntity mapUiToCountryEntity(String countryCode) {
        Locale loc = new Locale("", countryCode);
        return new CountryEntity(countryCode, loc.getDisplayCountry());
    }
}
