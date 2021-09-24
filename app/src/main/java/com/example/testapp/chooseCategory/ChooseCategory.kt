package com.example.testapp.chooseCategory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentChooseCategoryBinding
import com.example.testapp.model.CategoryModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ChooseCategory : Fragment(R.layout.fragment_choose_category),
    ChooseCategoryAdapter.OnCategorySelectedListener {
    private val TAG = ChooseCategory::class.java.simpleName
    private val fragmentChooseCategoryBinding by viewBinding(FragmentChooseCategoryBinding::bind)

    // collections
    private var categoryList: MutableList<CategoryModel> = mutableListOf()

    // adapter
    private lateinit var chooseCategoryAdapter: ChooseCategoryAdapter

    //viewmodel
    private lateinit var chooseCategoryViewModel: ChooseCategoryViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        Log.e(TAG, "On create view started..")
        init()
        setObservers()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun init() {
        chooseCategoryViewModel =
            ViewModelProvider(requireActivity()).get(ChooseCategoryViewModel::class.java)
        chooseCategoryViewModel.getCategoryList()

    }

    fun setObservers() {
        chooseCategoryViewModel.categoryLiveDataList.observe(viewLifecycleOwner, Observer {
            categoryList.clear()
            categoryList = it
            chooseCategoryAdapter = ChooseCategoryAdapter(requireContext(), categoryList, this)
            fragmentChooseCategoryBinding.productRecyclerView.itemAnimator = DefaultItemAnimator()
            fragmentChooseCategoryBinding.productRecyclerView.layoutManager =
                LinearLayoutManager(context)
            fragmentChooseCategoryBinding.productRecyclerView.adapter = chooseCategoryAdapter
        })
    }

    fun removeObservers() {
        chooseCategoryViewModel.categoryLiveDataList.removeObservers(viewLifecycleOwner)
    }

    override fun onCategorySelected(category: CategoryModel) {
        val args = Bundle()
        args.putParcelable("Category", category)
        Navigation.findNavController(fragmentChooseCategoryBinding.root)
            .navigate(R.id.action_category_to_books, args)
    }

    override fun onDestroyView() {
        removeObservers()
        super.onDestroyView()
    }
}