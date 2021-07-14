package com.kandc_android_challenge.data.repositories

import com.google.gson.JsonArray
import com.kandc_android_challenge.data.models.Result
import com.kandc_android_challenge.data.services.ContactService
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.domain.repositories.ContactRepository
import com.kandc_android_challenge.mapper.ApiContactMap
import com.kandc_android_challenge.utils.handleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactRepositoryImpl(private val contactService: ContactService) : ContactRepository {

    override suspend fun getContacts(): Result<List<Contact>> = suspendCoroutine { coroutine ->
        val call = contactService.getContacts()
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                response.handleResponse(
                    continuation = coroutine,
                    transformation = { jsonArray ->
                        jsonArray.map { ApiContactMap.map(it) }
                    }
                )
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                coroutine.resume(Result.failure<Exception>(t.fillInStackTrace()))
            }
        })
    }
}
