package com.petro.scope104.data.network

import com.petro.scope104.data.network.response.UserListResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("api")
    fun listRepos(
        @Query("nat") nationality: String?,
        @Query("page") page: Int,
        @Query("results") limit: Int,
        @Query("gender") gender: String?
    ): Observable<UserListResponse>
}