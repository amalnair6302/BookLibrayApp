package com.example.testapp.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class CategoryModel(
    var imageSource: String? = null,
    var categoryName: String? = null
) : Parcelable {
    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }
}