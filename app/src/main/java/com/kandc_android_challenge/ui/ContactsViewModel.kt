package com.kandc_android_challenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kandc_android_challenge.data.models.Result
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.domain.usecases.GetContactsUseCase

class ContactsViewModel(private val getContactsUseCase: GetContactsUseCase) : ViewModel() {

    fun getContacts(): LiveData<Result<List<Contact>>> = liveData { emit(getContactsUseCase()) }
}
