package com.kandc_android_challenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: String,
    val name: String,
    val companyName: String,
    var isFavorite: Boolean,
    val smallImageURL: String,
    val largeImageURL: String,
    val emailAddress: String?,
    val birthdate: String?,
    val phone: PhoneInfo,
    val address: AddressInfo?
) : Parcelable
