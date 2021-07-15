package com.kandc_android_challenge.domain.usecases

import com.kandc_android_challenge.domain.repositories.ContactRepository

class GetContactsUseCase(private val contactRepository: ContactRepository) {
    suspend operator fun invoke() = contactRepository.getContacts()
}
