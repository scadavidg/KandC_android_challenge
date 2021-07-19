package com.kandc_android_challenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ElementInfoProfile(
    val topLabel: String,
    val value: String,
    val rightLabel: String?,
) : Parcelable
