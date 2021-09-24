package com.example.testapp.webAPIClient


import com.example.testapp.model.Headlines
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

   /* @GET("/?mime_type=image")
    fun getPageDetail(@Query("page") Page: String?): Single<String?>*/

    // fun getPageDetail(@Query("?mime_type") type: String?,@Query("page") page:String): Call<String?>?


    @Headers("Content-Type: application/json")
    @GET("books/")
    fun getPageDetail(@Query("mime_type") type: String?,@Query("page") page:String,@Query("topic") topic:String,@Query("search") searchText:String): Call<Headlines>

    @Headers("Content-Type: application/json")
    @GET("books/")
    fun getSearchDetail(@Query("mime_type") type: String?,@Query("topic") topic:String,@Query("search") searchText:String): Call<Headlines>

}