package com.smolentsev.pixpicture.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smolentsev.pixpicture.data.RepositoryAppImpl
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.domain.usecases.GetCategoryListUseCase

class CategoryViewModel: ViewModel() {
    private val repository = RepositoryAppImpl
    private val getCategoryListUseCase = GetCategoryListUseCase(repository)

    val categoryList = getCategoryListUseCase()



}