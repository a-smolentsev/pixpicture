package com.smolentsev.pixpicture.domain.repository

import com.smolentsev.pixpicture.data.RetrofitInstance

class RepositoryImages {
    suspend fun getImagesByCategory(category: String, page: Int) =
        RetrofitInstance.api.getImage(category.lowercase(), page)
}