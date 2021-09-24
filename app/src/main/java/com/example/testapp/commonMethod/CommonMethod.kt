package com.example.testapp.commonMethod

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.net.NetworkInfo

import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.testapp.R


class CommonMethod {

companion object{
    fun loadimage(context: Context, imageView: ImageView, imageurl: String?, TAG: String?): Boolean {
        val complete = booleanArrayOf(false)
        Glide.with(context)
            .load(context.resources.getIdentifier(imageurl, "drawable", context.packageName))
            .priority(Priority.IMMEDIATE)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                    Log.e(TAG, "Loading failed " + e.toString())
                    complete[0] = false
                    return false
                }

                override fun onResourceReady(resource: Drawable?,model: Any,target: Target<Drawable?>,dataSource: DataSource,isFirstResource: Boolean): Boolean {
                    Log.e(TAG, "Resource is ready ")
                    complete[0] = true
                    return false
                }
            })
            .into(imageView)
        return complete[0]
    }

    fun isNetworkConnectionAvailable(context: Context): Boolean{
        var result = false
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
            else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
            result = false
        }
        return result
    }

    fun showPopUp(message: String?,context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.error_message)
            .setMessage(message)
            .setPositiveButton(R.string.ok_text) { dialog, which -> dialog.dismiss() }.show()
    }

    fun hideKeyboard(view: View,context: Context) {
        if (view != null) {
            Log.e("TAG", "Inside hide keyboard")
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            Log.e("TAG", "View is null")
        }
    }

}

}