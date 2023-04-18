package com.petro.scope104.data.network

import com.petro.scope104.data.UserDataSource
import com.petro.scope104.data.network.response.UserListResponse
import com.petro.scope104.data.network.response.UserResponse
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.presentation.list.Gender
import io.reactivex.rxjava3.core.Observable
import java.util.stream.Collectors
import javax.inject.Inject

class UserDataSourceNetworkImpl @Inject constructor(
    private val service: GitHubService,
    private val networkConverter: NetworkConverter
) : UserDataSource {
    override fun loadUsers(
        pageNumber: Int,
        pageSize: Int,
        gender: Gender,
        countries: List<String>
    ): Observable<List<WorkerEntity>> {
        val apiResponseLiveData: Observable<UserListResponse> = service.listRepos(
            java.lang.String.join(",", countries),
            pageNumber,
            pageSize,
            gender.serverName
        )
        return apiResponseLiveData.map<List<WorkerEntity>> { input: UserListResponse ->
            input.results.stream()
                .map { user: UserResponse -> networkConverter.mapNetworkToUi(user) }
                .collect(Collectors.toList())
        }
    }

    override fun saveUsers(users: List<WorkerEntity>) {
        throw IllegalStateException("Server not found!")
    }
}