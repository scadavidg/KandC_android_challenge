package com.kandc_android_challenge.di

import com.kandc_android_challenge.ui.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module(override = true) {
    // viewModels
    viewModel { ContactsViewModel(get()) }
}

val modules = listOf(applicationModule, domainModule, dataModule)
