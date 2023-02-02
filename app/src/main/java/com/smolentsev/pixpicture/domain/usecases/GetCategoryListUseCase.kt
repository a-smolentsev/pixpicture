package com.smolentsev.pixpicture.domain.usecases

import androidx.lifecycle.LiveData
import com.smolentsev.pixpicture.domain.repository.RepositoryApp
import com.smolentsev.pixpicture.domain.entity.Category

class GetCategoryListUseCase(private val repository: RepositoryApp) {
        operator fun invoke(): LiveData<List<Category>> {
            return repository.getCategoryList()
    }
}