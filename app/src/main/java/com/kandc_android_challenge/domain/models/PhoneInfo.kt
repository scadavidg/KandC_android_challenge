package com.kandc_android_challenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneInfo(
    val work: String?,
    val home: String?,
    val mobile: String?
) : Parcelable