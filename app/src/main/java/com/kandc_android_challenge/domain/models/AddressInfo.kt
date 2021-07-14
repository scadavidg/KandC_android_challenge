package com.kandc_android_challenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressInfo(
    val street: String?,
    val city: String?,
    val country: String?,
    val zipCode: Int?
) : Parcelable