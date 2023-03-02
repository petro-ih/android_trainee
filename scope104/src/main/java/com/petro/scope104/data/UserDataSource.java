package com.petro.scope104.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.petro.scope104.presentation.WorkerUi;
import com.petro.scope104.presentation.list.Gender;

import java.util.List;

public interface UserDataSource {
    LiveData<List<WorkerUi>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries);
    void saveUsers(List<WorkerUi> users);
}
