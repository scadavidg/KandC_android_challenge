package com.kandc_android_challenge.utils

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kandc_android_challenge.data.models.Result
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

inline fun <param, result> Response<param>.handleResponse(
    continuation: Continuation<Result<result>>,
    transformation: (predicate: param) -> result
) {
    if (!this.isSuccessful) {
        continuation.resume(Result.failure<Exception>(Throwable(this.errorBody().toString())))
        return
    }
    try {
        continuation.resume(Result.success(transformation(this.body()!!)))
    } catch (t: NullPointerException) {
        continuation.resume(Result.failure<NullPointerException>(t))
    } catch (t: Throwable) {
        continuation.resume(Result.failure<Throwable>(t.fillInStackTrace()))
    }
}