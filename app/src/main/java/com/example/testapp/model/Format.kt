package com.example.testapp.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

@SuppressLint("ParcelCreator")
class Format : Parcelable {
    @SerializedName("text/html; charset=utf-8")
    @Expose
    var html: String? = null

    @SerializedName("text/plain")
    @Expose
    var text: String? = null

    @SerializedName("image/jpeg")
    @Expose
    var image: String? = null


    @SerializedName("application/pdf")
    @Expose
    var pdf: String? = null

    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }
}

