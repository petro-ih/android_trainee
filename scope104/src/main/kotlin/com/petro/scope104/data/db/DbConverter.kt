package com.petro.scope104.data.db

import com.petro.scope104.data.db.entity.CountryEntity
import com.petro.scope104.data.db.entity.UserEntity
import com.petro.scope104.domain.entity.WorkerEntity
import java.util.*
import javax.inject.Inject

class DbConverter @Inject constructor() {
    fun mapUiToDatabase(workerEntity: WorkerEntity): UserEntity {
        return UserEntity(
            workerEntity.username,
            workerEntity.avatarUrl,
            workerEntity.avatarUrlXXL,
            workerEntity.firstName,
            workerEntity.lastName,
            workerEntity.dob,
            workerEntity.age,
            workerEntity.city,
            workerEntity.country,
            workerEntity.nat,
            workerEntity.phone,
            workerEntity.email,
            workerEntity.registered,
            workerEntity.isMale
        )
    }

    fun mapUiToCountryEntity(countryCode: String): CountryEntity {
        val loc = Locale("", countryCode)
        return CountryEntity(countryCode, loc.displayCountry)
    }

    fun mapDatabaseToUi(userEntity: UserEntity): WorkerEntity {
        return WorkerEntity(
            userEntity.avatarUrl,
            userEntity.avatarUrlXXL,
            userEntity.firstName,
            userEntity.lastName,
            userEntity.dob,
            userEntity.age,
            userEntity.city,
            userEntity.country,
            userEntity.phone,
            userEntity.username,
            userEntity.email,
            userEntity.nat,
            userEntity.registered,
            userEntity.isMale
        )
    }
}