package com.petro.scope104.domain.entity

import java.io.Serializable
import java.util.*

class WorkerEntity(
    var avatarUrl: String,
    val avatarUrlXXL: String,
    val firstName: String,
    var lastName: String,
    var dob: Date,
    val age: Int,
    var city: String,
    var country: String,
    val phone: String,
    val username: String,
    val email: String,
    val nat: String,
    val registered: Date,
    val isMale: Boolean
) : Serializable {

    val name: String
        get() = "$firstName $lastName"
    val natCountry: String
        get() = Locale("", nat).displayCountry

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val workerEntity = o as WorkerEntity
        return age == workerEntity.age && avatarUrl == workerEntity.avatarUrl && avatarUrlXXL == workerEntity.avatarUrlXXL && firstName == workerEntity.firstName && lastName == workerEntity.lastName && dob == workerEntity.dob && city == workerEntity.city && country == workerEntity.country && phone == workerEntity.phone && username == workerEntity.username && email == workerEntity.email && nat == workerEntity.nat && registered == workerEntity.registered && isMale == workerEntity.isMale
    }

    override fun hashCode(): Int {
        return Objects.hash(
            avatarUrl,
            avatarUrlXXL,
            firstName,
            lastName,
            dob,
            age,
            city,
            country,
            phone,
            username,
            email,
            nat,
            registered,
            isMale
        )
    }
}