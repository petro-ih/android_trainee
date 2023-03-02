package com.petro.scope104.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.petro.scope104.presentation.WorkerUi;
import com.petro.scope104.presentation.list.Gender;

import java.util.List;

public class UserDataRepository {
    private final UserDataSource dataBaseDataSource;
    private final UserDataSource networkDataSource;

    public UserDataRepository(UserDataSource dataBaseDataSource, UserDataSource networkDataSource) {
        this.dataBaseDataSource = dataBaseDataSource;
        this.networkDataSource = networkDataSource;
    }

    public LiveData<List<WorkerUi>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        final LiveData<List<WorkerUi>> dbLiveData = dataBaseDataSource.loadUsers(pageNumber, pageSize, gender, countries);
        return Transformations.switchMap(dbLiveData, dbInput -> {
            if (dbInput.size() >= pageSize) {
                return new MutableLiveData<>(dbInput);
            }else {
                final LiveData<List<WorkerUi>> networkLiveData = networkDataSource.loadUsers(pageNumber, pageSize, gender, countries);
                return Transformations.map(networkLiveData, networkInput -> {
                    dataBaseDataSource.saveUsers(networkInput);
                    return networkInput;
                });
            }
        });
    }
}
