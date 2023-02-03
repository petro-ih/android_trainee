package com.petro.scope104.util;

import com.petro.scope104.network.response.UserResponse;
import com.petro.scope104.ui.WorkerUi;

public class WorkerUIMapper {
    public static WorkerUi map(UserResponse user) {
        return new WorkerUi(user.picture.medium, user.picture.large, user.name.first + " " + user.name.last, user.dob.date, user.location.city + ", " + user.location.country, user.dob.age, user.phone, user.login.username, user.email, user.nat, user.registered.date);
    }
}
