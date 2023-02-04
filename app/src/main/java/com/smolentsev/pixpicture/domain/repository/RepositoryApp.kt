package com.smolentsev.pixpicture.domain.repository

import androidx.lifecycle.LiveData
import com.smolentsev.pixpicture.domain.entity.Category

interface RepositoryApp {
    fun getCategoryList(): LiveData<List<Category>>
}