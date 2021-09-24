package com.example.testapp.chooseBook

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.chooseCategory.ChooseCategoryAdapter
import com.example.testapp.databinding.ItemBooksBinding
import com.example.testapp.model.Format
import com.example.testapp.model.Results

class ChooseBookAdapter(private val context: Context?, val onBookSelectedListener: ChooseBookFragment) : RecyclerView.Adapter<ChooseBookAdapter.ProductViewHolder>() {

    private val bookList = mutableListOf<Results>()
    private val TAG: String = ChooseCategoryAdapter::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBooksBinding = ItemBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBooksBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val book = bookList[position]
        var authorName = ""
        var authorNameNew = ""
        if (book.author.size > 0) {
            authorName = book.author[0].name
        }
        if (book.author.size > 1) {
            for (i in 1 until book.author.size) {
                authorNameNew = book.author[i].name
                authorName = "$authorName,$authorNameNew"
            }

        }
        val imageFormat = book.format
        holder.itemBooksBinding.bookTitle.text = book.title
        holder.itemBooksBinding.bookAuthor.text = authorName
        Glide.with(context!!).load(imageFormat.image).into(holder.itemBooksBinding.bookCoverImage);
        holder.itemBooksBinding.categoryCardLayout.setOnClickListener {
            onBookSelectedListener.onBookSelected(book.format)
            Log.e(TAG, "Category selected:- ${book.format.image})")
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun addItems(newBookList: List<Results>, isSearch: Boolean) {

        if (bookList.isNotEmpty() && !isSearch) {
            val lastIndex = bookList.lastIndex
            bookList.addAll(lastIndex + 1, newBookList)
            notifyItemRangeInserted(lastIndex + 1, newBookList.size)
        } else {
            bookList.clear()
            bookList.addAll(newBookList)
            if (isSearch) {
                notifyDataSetChanged()
            }
            else{
                notifyItemRangeInserted(0, newBookList.size)
            }
        }
    }

    class ProductViewHolder(val itemBooksBinding: ItemBooksBinding) : RecyclerView.ViewHolder(itemBooksBinding.root)

    interface OnBookelectedListener {
        fun onBookSelected(format: Format)
    }

}