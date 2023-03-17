package com.petro.scope104.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.petro.scope104.domain.entity.WorkerEntity;
import com.petro.scope104.presentation.list.Gender;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface UserDataSource {
    Observable<List<WorkerEntity>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries);
    void saveUsers(List<WorkerEntity> users);
}
