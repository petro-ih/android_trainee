package com.petro.scope104.data.network;

import com.petro.scope104.data.network.response.UserListResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("api")
    Observable<UserListResponse> listRepos(
            @Query("nat") String nationality,
            @Query("page") int page,
            @Query("results") int limit,
            @Query("gender") String gender
    );
}