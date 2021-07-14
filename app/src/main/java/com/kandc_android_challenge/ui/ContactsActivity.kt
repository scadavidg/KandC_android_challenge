package com.kandc_android_challenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kandc_android_challenge.databinding.ActivityContactsBinding
import com.kandc_android_challenge.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityContactsBinding::inflate)
    private val contactsViewModel: ContactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
    }
}