package com.petro.scope104.data.network;

import com.petro.scope104.data.network.response.UserResponse;
import com.petro.scope104.domain.entity.WorkerEntity;

import javax.inject.Inject;

public class NetworkConverter {
    @Inject
    public NetworkConverter(){

    }
    public WorkerEntity mapNetworkToUi(UserResponse user) {
        return new WorkerEntity(user.picture.medium, user.picture.large, user.name.first, user.name.last, user.dob.date, user.dob.age, user.location.city, user.location.country, user.phone, user.login.username, user.email, user.nat, user.registered.date, user.gender.equals("male"));
    }
}
