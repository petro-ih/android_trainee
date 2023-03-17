package com.petro.scope104.data.network;

import androidx.annotation.NonNull;


import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.network.response.UserListResponse;
import com.petro.scope104.domain.entity.WorkerEntity;
import com.petro.scope104.presentation.list.Gender;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class UserDataSourceNetworkImpl implements UserDataSource {
    private final GitHubService service;
    private final NetworkConverter networkConverter;
    @Inject
    public UserDataSourceNetworkImpl(GitHubService service, NetworkConverter networkConverter) {
        this.service = service;
        this.networkConverter = networkConverter;
    }

    @Override
    public Observable<List<WorkerEntity>> loadUsers(int pageNumber, int pageSize, @NonNull Gender gender, @NonNull List<String> countries) {
        final Observable<UserListResponse> apiResponseLiveData = service.listRepos(String.join(",", countries), pageNumber, pageSize, gender.serverName);
        return apiResponseLiveData.map(input -> input.results.stream().map(networkConverter::mapNetworkToUi).collect(Collectors.toList()));
    }

    @Override
    public void saveUsers(List<WorkerEntity> users) {
        throw new IllegalStateException("Server not found!");
    }
}
