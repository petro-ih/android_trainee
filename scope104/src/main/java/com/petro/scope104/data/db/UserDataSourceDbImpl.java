package com.petro.scope104.data.db;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.db.dao.CountryDao;
import com.petro.scope104.data.db.dao.UserDao;
import com.petro.scope104.data.db.entity.CountryEntity;
import com.petro.scope104.data.db.entity.UserEntity;
import com.petro.scope104.presentation.WorkerUi;
import com.petro.scope104.presentation.list.Gender;
import com.petro.scope104.util.WorkerUIMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserDataSourceDbImpl implements UserDataSource {
    private final UserDao userDao;
    private final CountryDao countryDao;

    public UserDataSourceDbImpl(UserDao userDao, CountryDao countryDao) {
        this.userDao = userDao;
        this.countryDao = countryDao;
    }

    @Override
    public LiveData<List<WorkerUi>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        LiveData<List<UserEntity>> usersResult;
        Boolean isMale = null;
        if (gender == Gender.MALE) {
            isMale = true;
        } else if (gender == Gender.FEMALE) {
            isMale = false;
        }
        if (countries.isEmpty()) {
            usersResult = userDao.loadUsers(pageNumber, pageSize, isMale);
        } else {
            usersResult = userDao.loadUsers(pageNumber, pageSize, isMale, countries);
        }
        return Transformations.map(usersResult, input -> input.stream().map(WorkerUIMapper::mapDatabaseToUi).collect(Collectors.toList()));
    }

    @Override
    public void saveUsers(List<WorkerUi> users) {
        List<UserEntity> listDB = users.stream().map(WorkerUIMapper::mapUiToDatabase).collect(Collectors.toList());
        List<CountryEntity> countryList = users.stream().map(WorkerUi::getNat).map(WorkerUIMapper::mapUiToCountryEntity).collect(Collectors.toList());
        userDao.insert(listDB);
        countryDao.insert(countryList);
    }
}
