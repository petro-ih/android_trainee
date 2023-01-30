package com.petro.scope104.network;

import com.petro.scope104.network.response.UserListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
  @GET("api")
  Call<UserListResponse> listRepos(
          @Query("nat") String nationality,
          @Query("page") int page,
          @Query("results") int limit,
          @Query("seed") String seed);
}