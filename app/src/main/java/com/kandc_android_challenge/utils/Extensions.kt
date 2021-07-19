package com.kandc_android_challenge.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kandc_android_challenge.R
import com.kandc_android_challenge.data.models.Result
import retrofit2.Response
import java.io.Serializable
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <param, result> Response<param>.handleResponse(
    continuation: Continuation<Result<result>>,
    transformation: (predicate: param) -> result
) {
    if (!this.isSuccessful) {
        continuation.resume(
            Result.failure<BackendException>(
                BackendException(
                    this.errorBody().toString()
                )
            )
        )
        return
    }
    try {
        continuation.resume(Result.success(transformation(this.body()!!)))
    } catch (t: NullPointerException) {
        continuation.resume(Result.failure<CorruptedDataException>(t))
    } catch (t: Throwable) {
        continuation.resume(Result.failure<Throwable>(t))
    }
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun Context.showToast(@StringRes message: Int, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun <T : Serializable> Bundle.mustHaveSerializableExtra(argument: String): T {
    if (this.containsKey(argument)) {
        return this.getSerializable(argument)!! as T
    } else {
        throw IllegalArgumentException("Intent should contain argument $argument")
    }
}

fun <T : Parcelable> Bundle.mustHaveParcelableExtra(argument: String): T {
    if (this.containsKey(argument)) {
        return this.getParcelable(argument)!!
    } else {
        throw IllegalArgumentException("Intent should contain argument $argument")
    }
}

fun Any.getSimpleName(): String {
    return this.javaClass.simpleName
}

fun <T> Context.shouldImplement(myInterface: Class<*>): T {
    if (myInterface.isAssignableFrom(this::class.java)) {
        return this as T
    } else {
        throw InterfaceNotImplementedException(
            this.getSimpleName(),
            myInterface.simpleName
        )
    }
}

fun <T> ArrayList<T>.replaceItems(newItems: List<T>): ArrayList<T> = this.apply {
    clear()
    addAll(newItems)
}

fun View.setAsVisible() {
    this.visibility = View.VISIBLE
}

fun View.setAsInvisible() {
    this.visibility = View.INVISIBLE
}

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)

fun RecyclerView.ViewHolder.getDrawableCompat(id: Int): Drawable? =
    this.itemView.context.getDrawableCompat(id)

fun loadItemPicture(picture: String?, imageView: AppCompatImageView) {
    Glide.with(imageView.context)
        .load(picture)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .error(R.drawable.user_large)
        .into(imageView)
}

fun loadItemPicture(drawable: Drawable?, imageView: AppCompatImageView) {
    Glide.with(imageView.context)
        .load(drawable)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .error(R.drawable.user_large)
        .into(imageView)
}
