package com.smolentsev.pixpicture.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smolentsev.pixpicture.domain.repository.RepositoryImages

class ImagesListViewModelFactory(
    val app: Application,
    val imagesRepo: RepositoryImages
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImageListViewModel(app, imagesRepo) as T
        }
    }