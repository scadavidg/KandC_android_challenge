package com.tests.services

import com.example.kandc_android_challenge.BuildConfig.API_URL
import com.kandc_android_challenge.data.services.APIService
import com.kandc_android_challenge.utils.ROUTE_API_CONTACTS
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Protocol.HTTP_2
import okhttp3.Response
import org.junit.Before
import org.junit.Test

class APIServiceTest {

    @MockK
    private lateinit var client: OkHttpClient

    private val apiService = APIService()

    @Before
    fun setup() {
        client = mockk(relaxed = true)
    }

    @Test
    fun `getContactsList with mockedCallback`() {
        val mockedCall = mockk<Call>(relaxed = true)

        every { mockedCall.enqueue(any()) } answers {
            val callback = args[0] as Callback
            val fakeRequest = okhttp3.Request.Builder().url(API_URL + ROUTE_API_CONTACTS).build()
            val errorResponse = Response.Builder()
                .request(fakeRequest)
                .protocol(HTTP_2)
                .code(400)
                .message("Error")
                .build()
            callback.onResponse(mockedCall, errorResponse)
        }

        every {
            client.newCall(any())
        } returns mockedCall

        runBlocking {
            apiService.getContactsList()
        }
    }
}
