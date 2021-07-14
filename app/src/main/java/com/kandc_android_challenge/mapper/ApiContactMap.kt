package com.kandc_android_challenge.mapper

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.kandc_android_challenge.domain.models.Contact

object ApiContactMap {
    fun map(json: JsonElement): Contact {
        return Gson().fromJson(json, Contact::class.java)
    }
}