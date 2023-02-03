package com.smolentsev.pixpicture.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.presentation.adapter.CategoryAdapter
import com.smolentsev.pixpicture.presentation.viewmodel.CategoryViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoryFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        viewModel.categoryList.observe(viewLifecycleOwner) {
            Log.d("Category", it.toString())
            categoryAdapter.category = it
        }
        setupRecycleView(view)
        Log.d("recycler", setupRecycleView(view).toString())

    }

    private fun setupRecycleView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.category_recyclerview)
        categoryAdapter = CategoryAdapter()
        recyclerView.adapter = categoryAdapter
        clickItemListener()
    }

    private fun clickItemListener() {
        categoryAdapter.onShopItemClickListener = {
            launchCategoryImages(it)
            Log.d("Click_button", it.toString())
        }
    }

    private fun launchCategoryImages(category: Category) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ImagesListFragment.newInstance(category))
            .addToBackStack(null)
            .commit()
    }


}