package com.petro.scope104.data.db;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.db.dao.CountryDao;
import com.petro.scope104.data.db.dao.UserDao;
import com.petro.scope104.data.db.entity.CountryEntity;
import com.petro.scope104.data.db.entity.UserEntity;
import com.petro.scope104.domain.entity.WorkerEntity;
import com.petro.scope104.presentation.list.Gender;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class UserDataSourceDbImpl implements UserDataSource {
    private final UserDao userDao;
    private final CountryDao countryDao;
    private final DbConverter dbConverter;

    @Inject
    public UserDataSourceDbImpl(UserDao userDao, CountryDao countryDao, DbConverter dbConverter) {
        this.userDao = userDao;
        this.countryDao = countryDao;
        this.dbConverter = dbConverter;
    }

    @Override
    public Observable<List<WorkerEntity>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        Observable<List<UserEntity>> usersResult;
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
        return usersResult.map(input -> input.stream().map(dbConverter::mapDatabaseToUi).collect(Collectors.toList()));
    }

    @Override
    public void saveUsers(List<WorkerEntity> users) {
        List<UserEntity> listDB = users.stream().map(dbConverter::mapUiToDatabase).collect(Collectors.toList());
        List<CountryEntity> countryList = users.stream().map(WorkerEntity::getNat).map(dbConverter::mapUiToCountryEntity).collect(Collectors.toList());
        userDao.insert(listDB);
        countryDao.insert(countryList);
    }
}