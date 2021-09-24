package com.example.testapp.chooseCategory

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.commonMethod.CommonMethod
import com.example.testapp.databinding.ItemCategoryBinding
import com.example.testapp.model.CategoryModel

class ChooseCategoryAdapter(private val context: Context?, val categoryList: List<CategoryModel>,val onCategorySelectedListener: OnCategorySelectedListener): RecyclerView.Adapter<ChooseCategoryAdapter.ProductViewHolder>() {
    private val TAG: String = ChooseCategoryAdapter::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemCategoryBinding)
    }

    override fun onBindViewHolder(holder:ProductViewHolder, position: Int) {
        val category = categoryList[position]
        holder.itemCategoryBinding.categoryName.text=category.categoryName

        CommonMethod.loadimage(context!!, holder.itemCategoryBinding.categoryImage, "@drawable/${category.imageSource}", TAG)

        holder.itemCategoryBinding.categoryCardLayout.setOnClickListener {
            onCategorySelectedListener.onCategorySelected(category)
            Log.e(TAG, "Category selected:- ${category.categoryName})")
        }


    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ProductViewHolder(val itemCategoryBinding: ItemCategoryBinding) : RecyclerView.ViewHolder(itemCategoryBinding.root)


    interface OnCategorySelectedListener {
        fun onCategorySelected(category:CategoryModel)
    }
}