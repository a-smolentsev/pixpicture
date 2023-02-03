package com.smolentsev.pixpicture.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.constants.Companion.FAIL_LOAD
import com.smolentsev.pixpicture.constants.Companion.LOADING
import com.smolentsev.pixpicture.constants.Companion.SUCCESS_LOAD
import com.smolentsev.pixpicture.data.Hit
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.presentation.adapter.ImageAllAdapter
import com.smolentsev.pixpicture.presentation.viewmodel.ImageListViewModel


class ImagesListFragment : Fragment() {
    private lateinit var category: Category
    private lateinit var nameCategory: TextView
    private lateinit var wallpaper: Hit
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ImageListViewModel
    private lateinit var imagePreviewAdapter: ImageAllAdapter
    private lateinit var recyclerView: RecyclerView

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
        setupRecycleView()

        viewModel.stateLoading.observe(viewLifecycleOwner){
            when(it){
                LOADING -> progressBar.visibility = View.VISIBLE
                SUCCESS_LOAD -> {
                    progressBar.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
                FAIL_LOAD ->{
                    recyclerView.visibility = View.INVISIBLE

                }
            }
        }


    }
    private fun setupRecycleView() {
        imagePreviewAdapter = ImageAllAdapter()
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imagePreviewAdapter
        clickItemListener()
    }

    private fun setupView(view: View) {
        recyclerView = view.findViewById(R.id.allImageRV)
        nameCategory = view.findViewById(R.id.categoryName)
        progressBar = view.findViewById(R.id.progressBar)
    }
    private fun clickItemListener() {
        imagePreviewAdapter.onImageClickListener = {
            launchWallpaperFragment(it.largeImageURL)
            Log.d("Click_button", it.toString())
        }
    }

    private fun launchWallpaperFragment(imageURL:String){
        Log.d("Arguments0", imageURL)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,WallpaperFragment.newInstance(imageURL))
            .addToBackStack(null)
            .commit()
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