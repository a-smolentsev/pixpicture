package com.smolentsev.pixpicture.data

import com.smolentsev.pixpicture.constants.Companion.KEY_PUBLIC
import com.smolentsev.pixpicture.domain.entity.ImagesCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("/api/?key=$KEY_PUBLIC&per_page=20")
    suspend fun getImage(@Query("category") category: String,
    @Query("page") page: Int = 1):Response<ImagesCategory>
}