package com.petro.scope104.presentation.list;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petro.scope104.data.UserDataRepository;
import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.db.DataBase;
import com.petro.scope104.data.db.UserDataSourceDbImpl;
import com.petro.scope104.data.network.RetrofitInstance;
import com.petro.scope104.data.network.UserDataSourceNetworkImpl;
import com.petro.scope104.presentation.WorkerUi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WorkerListViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private final MutableLiveData<List<WorkerUi>> usersLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);
    private UserDataRepository userDataRepository;
    private int currentPageNumber = 0;

    public LiveData<List<WorkerUi>> getUsers() {
        return usersLiveData;
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public void init(Context context) {
        DataBase dataBase = DataBase.getDataBase(context);
        UserDataSource networkDataSource = new UserDataSourceNetworkImpl(RetrofitInstance.INSTANCE.service);
        UserDataSource dataBaseDataSource = new UserDataSourceDbImpl(dataBase.userDao(), dataBase.countryDao());
        userDataRepository = new UserDataRepository(dataBaseDataSource, networkDataSource);
    }

    private void setLoading(Boolean isLoading) {
        isLoadingLiveData.setValue(isLoading);
    }

    public void loadMore(boolean refresh, Gender gender, Set<String> countries) {
        Log.d("userlist", "refreshData: ");
        setLoading(true);
        if (refresh) {
            currentPageNumber = 0;
        }
        userDataRepository.loadUsers(currentPageNumber++, PAGE_SIZE, gender, new ArrayList<>(countries)).observeForever(workerUis -> {
            List<WorkerUi> newList;
            if (refresh) {
                newList = new ArrayList<>();
            } else {
                newList = new ArrayList<>(Objects.requireNonNull(usersLiveData.getValue()));
            }
            newList.addAll(workerUis);
            usersLiveData.postValue(newList);
            setLoading(false);
        });
    }
}
