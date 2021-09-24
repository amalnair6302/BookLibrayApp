package com.example.testapp.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressLint("ParcelCreator")
public class Results implements Parcelable {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("authors")
    @Expose
    private List<Authors> author;

    @SerializedName("formats")
    @Expose
    private Format format;

    @SerializedName("title")
    @Expose
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Authors> getAuthor() {
        return author;
    }

    public void setAuthor(List<Authors> author) {
        this.author = author;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
