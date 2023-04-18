package com.petro.scope104.presentation.select

import java.io.Serializable

class SelectItem(
    val title: String?,
    var value: Boolean,
    val `object`: Serializable
    ) : Serializable