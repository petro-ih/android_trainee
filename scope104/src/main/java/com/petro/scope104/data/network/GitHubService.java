package com.petro.scope104.data.network;

import androidx.lifecycle.LiveData;

import com.petro.scope104.data.network.livedataadapter.ApiResponse;
import com.petro.scope104.data.network.response.UserListResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("api")
    LiveData<ApiResponse<UserListResponse>> listRepos(
            @Query("nat") String nationality,
            @Query("page") int page,
            @Query("results") int limit,
            @Query("gender") String gender
    );
}