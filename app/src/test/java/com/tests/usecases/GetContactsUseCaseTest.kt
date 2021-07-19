package com.tests.usecases

import com.kandc_android_challenge.data.models.Result
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.domain.repositories.ContactRepository
import com.kandc_android_challenge.domain.usecases.GetContactsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class GetContactsUseCaseTest {

    @MockK
    lateinit var contactRepository: ContactRepository

    private val contacts = mockk<List<Contact>>()

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setUp() {
        coEvery {
            contactRepository.getContacts()
        } returns Result.success(contacts)
    }

    @Test
    fun `When useCase is invoked then returns a list of contacts`() {
        val getContactsUseCase = GetContactsUseCase(contactRepository)

        val result = runBlocking {
            getContactsUseCase()
        }

        result shouldBeEqualTo Result.success(contacts)

        coVerify {
            contactRepository.getContacts()
        }
    }
}
