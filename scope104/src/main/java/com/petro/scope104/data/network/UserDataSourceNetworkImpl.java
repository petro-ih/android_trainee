package com.petro.scope104.data.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.network.livedataadapter.ApiResponse;
import com.petro.scope104.data.network.response.UserListResponse;
import com.petro.scope104.presentation.WorkerUi;
import com.petro.scope104.presentation.list.Gender;
import com.petro.scope104.util.WorkerUIMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataSourceNetworkImpl implements UserDataSource {
    public final GitHubService service;

    public UserDataSourceNetworkImpl(GitHubService service) {
        this.service = service;
    }

    @Override
    public LiveData<List<WorkerUi>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        final LiveData<ApiResponse<UserListResponse>> apiResponseLiveData = service.listRepos(String.join(",", countries), pageNumber, pageSize, gender.serverName);
        return Transformations.map(apiResponseLiveData, input -> input.body != null ? input.body.results.stream().map(WorkerUIMapper::mapNetworkToUi).collect(Collectors.toList()) : new ArrayList<>());
    }

    @Override
    public void saveUsers(List<WorkerUi> users) {
        throw new IllegalStateException("Server not found!");
    }
}
