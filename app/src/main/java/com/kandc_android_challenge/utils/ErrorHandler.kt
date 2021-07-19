package com.kandc_android_challenge.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kandc_android_challenge.R
import timber.log.Timber

interface ErrorHandler {

    fun handleError(t: Throwable) {
        Timber.e(t)
        with(getContext()) {
            when (t) {
                is BackendException -> t.message?.let { showToast(it) }
                    ?: showToast(R.string.error_default)
                is CorruptedDataException -> showToast(R.string.error_corrupted_data)
                else -> showToast(R.string.error_default)
            }
        }
    }

    private fun getContext(): Context {
        return when (this) {
            is AppCompatActivity -> this
            is Fragment -> requireContext()
            else -> throw IllegalStateException("This interface needs to be implemented by an Activity or a Fragment")
        }
    }
}
