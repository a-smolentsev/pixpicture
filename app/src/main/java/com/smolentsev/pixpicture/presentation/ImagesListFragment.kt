package com.smolentsev.pixpicture.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.presentation.adapter.ImageAllAdapter
import com.smolentsev.pixpicture.presentation.viewmodel.ImageListViewModel


class ImagesListFragment : Fragment() {
    private lateinit var category: Category
    private lateinit var nameCategory: TextView
    private lateinit var viewModel: ImageListViewModel
    private lateinit var imagePreviewAdapter: ImageAllAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCategory()
        Log.d("fragment_Category",category.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_images_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        viewModel = ViewModelProvider(this)[ImageListViewModel::class.java]
        nameCategory.text = category.name
        viewModel.getImage(category.name)
        viewModel.image.observe(viewLifecycleOwner){
            Log.d("Category",it.toString())
            imagePreviewAdapter.image = it

        }
        setupRecycleView(view)


    }
    private fun setupRecycleView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.allImageRV)
        imagePreviewAdapter = ImageAllAdapter()
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imagePreviewAdapter
    }

    private fun setupView(view: View) {
        nameCategory = view.findViewById(R.id.categoryName)

    }

    private fun checkCategory(){
        category = requireArguments().getSerializable(CATEGORY_ID) as Category
    }
    companion object {
        private const val CATEGORY_ID = "category_id"

        @JvmStatic
        fun newInstance(category: Category): ImagesListFragment {
            return ImagesListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CATEGORY_ID, category)
                }
            }
        }
    }

}