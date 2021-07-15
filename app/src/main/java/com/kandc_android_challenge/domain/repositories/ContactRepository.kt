package com.kandc_android_challenge.domain.repositories

import com.kandc_android_challenge.data.models.Result
import com.kandc_android_challenge.domain.models.Contact

interface ContactRepository {

    suspend fun getContacts(): Result<List<Contact>>
}
