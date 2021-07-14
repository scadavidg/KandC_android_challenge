package com.kandc_android_challenge.data.services

class ContactService() {
    private val apiService = APIService()

    fun getContacts() = apiService.getContactsList()
}