package com.petro.scope104.data.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.network.livedataadapter.ApiResponse;
import com.petro.scope104.data.network.response.UserListResponse;
import com.petro.scope104.domain.entity.WorkerEntity;
import com.petro.scope104.presentation.list.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class UserDataSourceNetworkImpl implements UserDataSource {
    private final GitHubService service;
    private final NetworkConverter networkConverter;
    @Inject
    public UserDataSourceNetworkImpl(GitHubService service, NetworkConverter networkConverter) {
        this.service = service;
        this.networkConverter = networkConverter;
    }

    @Override
    public LiveData<List<WorkerEntity>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        final LiveData<ApiResponse<UserListResponse>> apiResponseLiveData = service.listRepos(String.join(",", countries), pageNumber, pageSize, gender.serverName);
        return Transformations.map(apiResponseLiveData, input -> input.body != null ? input.body.results.stream().map(networkConverter::mapNetworkToUi).collect(Collectors.toList()) : new ArrayList<>());
    }

    @Override
    public void saveUsers(List<WorkerEntity> users) {
        throw new IllegalStateException("Server not found!");
    }
}
