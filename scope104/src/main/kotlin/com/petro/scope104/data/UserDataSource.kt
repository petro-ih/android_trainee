package com.petro.scope104.data

import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.presentation.list.Gender
import io.reactivex.rxjava3.core.Observable

interface UserDataSource {
    fun loadUsers(
        pageNumber: Int,
        pageSize: Int,
        gender: Gender,
        countries: List<String>
    ): Observable<List<WorkerEntity>>

    fun saveUsers(users: List<WorkerEntity>)
}