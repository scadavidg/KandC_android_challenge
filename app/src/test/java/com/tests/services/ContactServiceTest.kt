package com.tests.services

import com.google.gson.JsonArray
import com.kandc_android_challenge.data.services.APIService
import com.kandc_android_challenge.data.services.ContactService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Call

class ContactServiceTest {

    @MockK
    lateinit var apiService: APIService

    private val contactService = ContactService()

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `getContactsList then returns Call-JsonArray`() {
        val call = mockk<Call<JsonArray>>()

        coEvery {
            apiService.getContactsList()
        } returns call

        runBlocking {
            contactService.getContacts()
        }
    }
}
