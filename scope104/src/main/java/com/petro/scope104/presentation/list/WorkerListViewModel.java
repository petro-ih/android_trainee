package com.petro.scope104.presentation.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petro.scope104.data.UserDataRepository;
import com.petro.scope104.domain.entity.WorkerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class WorkerListViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private final MutableLiveData<List<WorkerEntity>> usersLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);
    private final UserDataRepository userDataRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int currentPageNumber = 0;

    @Inject
    public WorkerListViewModel(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public LiveData<List<WorkerEntity>> getUsers() {
        return usersLiveData;
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
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
        Disposable disposable = userDataRepository
                .loadUsers(currentPageNumber++, PAGE_SIZE, gender, new ArrayList<>(countries))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workerUis -> {
                    List<WorkerEntity> newList;
                    if (refresh) {
                        newList = new ArrayList<>();
                    } else {
                        newList = new ArrayList<>(Objects.requireNonNull(usersLiveData.getValue()));
                    }
                    newList.addAll(workerUis);
                    usersLiveData.postValue(newList);
                    setLoading(false);
        }, e -> Log.d("LOGGG", "Error", e));
        compositeDisposable.add(disposable);
    }
}
