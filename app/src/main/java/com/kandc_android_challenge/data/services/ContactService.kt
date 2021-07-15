package com.kandc_android_challenge.data.services

import com.google.gson.JsonArray
import retrofit2.Call

class ContactService() {
    private val apiService = APIService()

    fun getContacts(): Call<JsonArray> = apiService.getContactsList()
}
