package com.kandc_android_challenge.di

import com.kandc_android_challenge.data.repositories.ContactRepositoryImpl
import com.kandc_android_challenge.domain.repositories.ContactRepository
import com.kandc_android_challenge.domain.useCases.GetContactsUseCase
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val domainModule = module(override = true) {
    //Repositories
    singleBy<ContactRepository, ContactRepositoryImpl>()

    //UseCases
    single { GetContactsUseCase(get()) }
}