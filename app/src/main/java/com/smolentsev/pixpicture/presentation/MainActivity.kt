package com.smolentsev.pixpicture.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.repository.RepositoryImages
import com.smolentsev.pixpicture.presentation.viewmodel.ImageListViewModel
import com.smolentsev.pixpicture.presentation.viewmodel.ImagesListViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ImageListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelProviderFactory = ImagesListViewModelFactory(application,
            RepositoryImages())
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ImageListViewModel::class.java)
    }
}