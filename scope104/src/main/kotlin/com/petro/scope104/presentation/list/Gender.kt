package com.petro.scope104.presentation.list

enum class Gender(val serverName: String?, val displayName: String) {
    MALE("male", "male"),
    FEMALE("female", "female"),
    UNKNOWN(null, "unknown");
}