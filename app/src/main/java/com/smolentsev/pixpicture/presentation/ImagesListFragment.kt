package com.smolentsev.pixpicture.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.presentation.adapter.ImageAllAdapter
import com.smolentsev.pixpicture.presentation.viewmodel.ImageListViewModel


class ImagesListFragment : Fragment() {
    private lateinit var category: Category
    private lateinit var nameCategory: TextView
    private lateinit var failInternet: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ImageListViewModel
    private lateinit var imagePreviewAdapter: ImageAllAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var categoryArgs: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCategory()
        Log.d("fragment_Category", category.name)
        categoryArgs =category.name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_images_list, container, false)

    }
    override fun onResume() {
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        viewModel = (activity as MainActivity).viewModel

        setupRecycleView()
        nameCategory.text = category.name
        viewModel.getImage(category.name)
        Log.d("images_categoryname", category.name)
        viewModel.allImages.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    progressBar.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                    failInternet.visibility = View.INVISIBLE
                    response.data?.let { result ->
                        imagePreviewAdapter.differ.submitList(result.hits.toList())
                        Log.d("images_adapter",result.hits.toString())
                        val totalPages = result.total / 20 + 2
                        isLastPage = viewModel.page == totalPages
                        if(isLastPage) {
                            recyclerView.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    recyclerView.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE
                    failInternet.visibility = View.VISIBLE
                    response.message?.let { message ->
                        Log.e("Error", "Ошибка $message")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    failInternet.visibility = View.INVISIBLE
                }
            }

        })

    }

    private fun setupRecycleView() {
        imagePreviewAdapter = ImageAllAdapter()

        recyclerView.apply {
          layoutManager = GridLayoutManager(context, 2)
          adapter = imagePreviewAdapter
          addOnScrollListener(this@ImagesListFragment.scrollListener)
        }

        clickItemListener()
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 20
            val shouldPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getImage(category.name)
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    private fun setupView(view: View) {
        recyclerView = view.findViewById(R.id.allImageRV)
        nameCategory = view.findViewById(R.id.categoryName)
        progressBar = view.findViewById(R.id.progressBar)
        failInternet = view.findViewById(R.id.failText)
    }

    private fun clickItemListener() {
        imagePreviewAdapter.onImageClickListener = {
            launchWallpaperFragment(it.largeImageURL)
            Log.d("Click_button", it.largeImageURL)
        }
    }

    private fun launchWallpaperFragment(imageURL: String) {
        Log.d("Arguments0", imageURL)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, WallpaperFragment.newInstance(imageURL))
            .addToBackStack(null)
            .commit()
    }

    private fun checkCategory() {
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