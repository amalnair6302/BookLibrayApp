package com.example.testapp.chooseCategory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.CategoryModel
import com.example.testapp.model.Headlines
import com.example.testapp.model.Results
import com.example.testapp.webAPIClient.ApiClient
import com.example.testapp.webAPIClient.ApiInterface
import retrofit2.Call
import retrofit2.Response

class ChooseCategoryViewModel:ViewModel(){

    private val TAG: String = ChooseCategoryViewModel::class.java.simpleName

     val categoryLiveDataList: MutableLiveData<MutableList<CategoryModel>> = MutableLiveData()

    fun getCategoryList() {
        var categoryList: MutableList<CategoryModel> = mutableListOf()
        val listOfImageNames = listOf(
            "fiction",
            "drama",
            "humour",
            "politics",
            "philosophy",
            "history",
            "adventure"
        )
        val listOfCategoryNames =
            listOf("FICTION", "DRAMA", "HUMOR", "POLITICS", "PHILOSOPHY", "HISTORY", "ADVENTURE")

        for (i in listOfCategoryNames.indices) {
            val categoryModel = CategoryModel()
            categoryModel.categoryName = listOfCategoryNames[i]
            categoryModel.imageSource = listOfImageNames[i]
            categoryList.add(categoryModel)
        }
        categoryLiveDataList.postValue(categoryList)

    }
}