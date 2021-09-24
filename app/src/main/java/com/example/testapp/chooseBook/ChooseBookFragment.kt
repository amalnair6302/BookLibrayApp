package com.example.testapp.chooseBook

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapp.R
import com.example.testapp.chooseCategory.ChooseCategory
import com.example.testapp.databinding.FragmentChooseBookBinding
import com.example.testapp.model.BookModel
import com.example.testapp.model.CategoryModel
import com.example.testapp.model.Format
import com.example.testapp.model.Results
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class ChooseBookFragment : Fragment(R.layout.fragment_choose_book),
    ChooseBookAdapter.OnBookelectedListener {
    private val TAG = ChooseCategory::class.java.simpleName
    private var category: CategoryModel? = null
    private val fragmentChooseBookBinding by viewBinding(FragmentChooseBookBinding::bind)

    // collections
    private val bookList: MutableList<BookModel> = mutableListOf()
    var resultList: MutableList<Results> = mutableListOf()

    // adapter
    private lateinit var chooseBookAdapter: ChooseBookAdapter

    private lateinit var chooseBookViewModel: ChooseBookViewModel

    var page=0
    var isLoading = true
    var searchText = ""
    var isSearch = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        setHasOptionsMenu(true)
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.e(TAG, "Home button is pressed")
                Navigation.findNavController(fragmentChooseBookBinding.root)
                    .navigate(R.id.action_books_to_category)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {

        chooseBookAdapter = ChooseBookAdapter(requireContext(),  this)
        fragmentChooseBookBinding.productRecyclerView.itemAnimator = DefaultItemAnimator()
        fragmentChooseBookBinding.productRecyclerView.layoutManager = GridLayoutManager(context, 3)
        fragmentChooseBookBinding.productRecyclerView.adapter = chooseBookAdapter
        chooseBookViewModel = ViewModelProvider(requireActivity()).get(ChooseBookViewModel::class.java)
        chooseBookViewModel.clearResultSet()
        isLoading = true
        val argsFromBookCategory = arguments
        if (argsFromBookCategory != null) {
            category = argsFromBookCategory.getParcelable("Category")
            Log.d(TAG, "value $category")
        }
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = category?.categoryName
        fragmentChooseBookBinding.progressBar.visibility = View.VISIBLE
        page=1
        if (isLoading) {
            chooseBookViewModel.getPageDetail(page, "image", category?.imageSource!!, searchText)
        }

        fragmentChooseBookBinding.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(value: CharSequence, p1: Int, p2: Int, p3: Int) {
                searchText = value.toString()
                isSearch=true
                if (searchText=="") {
                    page = 1
                    Log.e(TAG, "search Element $searchText & page $page")
                    if (!isLoading) {
                        fragmentChooseBookBinding.searchProgressBar.visibility = View.VISIBLE
                        chooseBookViewModel.getPageDetail(page, "image", category?.imageSource!!, "")
                    }
                } else {
                    Log.e(TAG, "search Element $searchText")
                    if (!isLoading) {
                        fragmentChooseBookBinding.searchProgressBar.visibility = View.VISIBLE
                        chooseBookViewModel.getPageDetail(page, "image", category?.imageSource!!, searchText)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        fragmentChooseBookBinding.bookScrollLayout.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if (!isLoading) {
                        page++
                        isLoading = true
                        fragmentChooseBookBinding.progressBar.visibility = View.VISIBLE
                        chooseBookViewModel.getPageDetail(page, "image", category?.imageSource!!, searchText)
                        isSearch=searchText!=""
                    }
                }
            }

        })

    }

    fun setObservers() {
        chooseBookViewModel.resultLiveDataList.observe(viewLifecycleOwner, Observer {
            chooseBookAdapter.addItems(it,isSearch)
            isLoading = false
            fragmentChooseBookBinding.progressBar.visibility = View.GONE
            fragmentChooseBookBinding.searchProgressBar.visibility = View.GONE
        })
    }

    fun removeObservers() {
        chooseBookViewModel.resultLiveDataList.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        Log.d(TAG,"View destroyed")
        removeObservers()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG,"Fragment destroyed")
        super.onDestroy()
    }

    override fun onBookSelected(format: Format) {
        val args = Bundle()
        args.putParcelable("Formats", format)
        Navigation.findNavController(fragmentChooseBookBinding.root)
            .navigate(R.id.action_books_to_webview, args)
    }

}