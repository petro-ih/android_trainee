package com.petro.scope104.data

import com.petro.scope104.data.qualifier.DataBaseQualifier
import com.petro.scope104.data.qualifier.Network
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.presentation.list.Gender
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    @param:DataBaseQualifier private val dataBaseDataSource: UserDataSource,
    @param:Network private val networkDataSource: UserDataSource
) {
    fun loadUsers(
        pageNumber: Int,
        pageSize: Int,
        gender: Gender,
        countries: List<String>
    ): Observable<List<WorkerEntity>> {
        val dbLiveData: Observable<List<WorkerEntity>> =
            dataBaseDataSource.loadUsers(pageNumber, pageSize, gender, countries)
        return dbLiveData.switchMap { dbInput: List<WorkerEntity> ->
            if (dbInput.size >= pageSize) {
                return@switchMap Observable.just<List<WorkerEntity>>(dbInput)
            } else {
                val networkLiveData: Observable<List<WorkerEntity>> =
                    networkDataSource.loadUsers(pageNumber, pageSize, gender, countries)
                return@switchMap networkLiveData.map<List<WorkerEntity>> { networkInput: List<WorkerEntity> ->
                    dataBaseDataSource.saveUsers(networkInput)
                    networkInput
                }
            }
        }
    }
}