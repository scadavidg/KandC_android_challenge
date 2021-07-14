package com.kandc_android_challenge.domain.useCases

import com.kandc_android_challenge.domain.repositories.ContactRepository

class GetContactsUseCase(private val contactRepository: ContactRepository) {
    suspend operator fun invoke() = contactRepository.getContacts()
}