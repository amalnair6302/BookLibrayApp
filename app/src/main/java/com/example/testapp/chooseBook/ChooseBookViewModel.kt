package com.example.testapp.chooseBook

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.Headlines
import com.example.testapp.model.Results
import com.example.testapp.webAPIClient.ApiClient
import com.example.testapp.webAPIClient.ApiInterface
import retrofit2.Call
import retrofit2.Response

class ChooseBookViewModel : ViewModel() {
    private val TAG: String = ChooseBookViewModel::class.java.simpleName
    private lateinit var call: Call<Headlines>

    var resultLiveDataList: MutableLiveData<MutableList<Results>> = MutableLiveData()


    fun clearResultSet(){
        resultLiveDataList = MutableLiveData()
    }

    fun getPageDetail(page: Int, type: String, topic: String,searchText :String) {
        Log.d(TAG,"API called for page $page")
        val apiInterface: ApiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        call = if(searchText == ""){
            apiInterface.getPageDetail(type, page.toString(), topic, searchText)
        }else{
            apiInterface.getSearchDetail(type, topic, searchText)
        }

        call.enqueue(object : retrofit2.Callback<Headlines> {
            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if (response.body() != null && response.isSuccessful && response.body()!!.results != null) {
                    val  bookList = response.body()!!.results
                    resultLiveDataList.postValue(bookList)
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                val responseResult = t.toString()
                Log.e(TAG, "Response $responseResult")
            }


        })
    }
}