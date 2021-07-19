package com.tests.repositories

import com.google.gson.JsonArray
import com.kandc_android_challenge.data.repositories.ContactRepositoryImpl
import com.kandc_android_challenge.data.services.ContactService
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactRepositoryTest {

    @MockK
    lateinit var contactService: ContactService

    private lateinit var contactRepository: ContactRepositoryImpl

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setUp() {
        contactRepository = ContactRepositoryImpl(contactService)
    }

    @Test
    fun `When getContacts is called then returns a contacts list`() {
        val call = mockk<Call<JsonArray>>()
        val response = mockk<Response<JsonArray>>()
        val slot = CapturingSlot<Callback<JsonArray>>()

        every {
            contactService.getContacts()
        } returns call

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, response)
        }

        every {
            response.isSuccessful
        } returns true

        runBlocking {
            contactRepository.getContacts()
        }

        coVerify {
            contactService.getContacts()
            call.enqueue(any())
        }
    }
}
