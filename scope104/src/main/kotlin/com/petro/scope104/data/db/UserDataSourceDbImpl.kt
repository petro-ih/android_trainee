package com.petro.scope104.data.db

import com.petro.scope104.data.UserDataSource
import com.petro.scope104.data.db.dao.CountryDao
import com.petro.scope104.data.db.dao.UserDao
import com.petro.scope104.data.db.entity.UserEntity
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.presentation.list.Gender
import io.reactivex.rxjava3.core.Observable
import java.util.stream.Collectors
import javax.inject.Inject

class UserDataSourceDbImpl @Inject constructor(
    private val userDao: UserDao,
    private val countryDao: CountryDao,
    private val dbConverter: DbConverter
) : UserDataSource {
    override fun loadUsers(
        pageNumber: Int,
        pageSize: Int,
        gender: Gender,
        countries: List<String>
    ): Observable<List<WorkerEntity>> {
        val usersResult: Observable<List<UserEntity>>
        var isMale: Boolean? = null
        if (gender == Gender.MALE) {
            isMale = true
        } else if (gender == Gender.FEMALE) {
            isMale = false
        }
        if (countries.isEmpty()) {
            usersResult = userDao.loadUsers(pageNumber, pageSize, isMale)
        } else {
            usersResult = userDao.loadUsers(pageNumber, pageSize, isMale, countries)
        }
        return usersResult.map<List<WorkerEntity>> { input: List<UserEntity> ->
            input.stream()
                .map { userEntity: UserEntity -> dbConverter.mapDatabaseToUi(userEntity) }
                .collect(Collectors.toList())
        }
    }

    override fun saveUsers(users: List<WorkerEntity>) {
        val listDB = users.stream()
            .map { workerEntity: WorkerEntity -> dbConverter.mapUiToDatabase(workerEntity) }
            .collect(Collectors.toList())
        val countryList = users.stream().map { obj: WorkerEntity -> obj.nat }
            .map { countryCode: String? ->
                dbConverter.mapUiToCountryEntity(
                    countryCode!!
                )
            }.collect(Collectors.toList())
        userDao.insert(listDB)
        countryDao.insert(countryList)
    }
}