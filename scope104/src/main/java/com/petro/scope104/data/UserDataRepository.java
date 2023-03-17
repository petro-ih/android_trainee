package com.petro.scope104.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.petro.scope104.data.qualifier.DataBaseQualifier;
import com.petro.scope104.data.qualifier.Network;
import com.petro.scope104.domain.entity.WorkerEntity;
import com.petro.scope104.presentation.list.Gender;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class UserDataRepository {
    private final UserDataSource dataBaseDataSource;
    private final UserDataSource networkDataSource;

    @Inject
    public UserDataRepository
            (@DataBaseQualifier UserDataSource dataBaseDataSource,
             @Network UserDataSource networkDataSource
    ) {
        this.dataBaseDataSource = dataBaseDataSource;
        this.networkDataSource = networkDataSource;
    }

    public Observable<List<WorkerEntity>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        final Observable<List<WorkerEntity>> dbLiveData = dataBaseDataSource.loadUsers(pageNumber, pageSize, gender, countries);
        return dbLiveData.switchMap(dbInput -> {
            if (dbInput.size() >= pageSize) {
                return Observable.just(dbInput);
            } else {
                final Observable<List<WorkerEntity>> networkLiveData = networkDataSource.loadUsers(pageNumber, pageSize, gender, countries);
                return networkLiveData.map(networkInput -> {
                    dataBaseDataSource.saveUsers(networkInput);
                    return networkInput;
                });
            }
        });
    }
}
