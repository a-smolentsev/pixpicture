package com.smolentsev.pixpicture.data

import com.smolentsev.pixpicture.constants.Companion.KEY_PUBLIC
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("/api/?key=$KEY_PUBLIC&per_page=200")
    suspend fun getImage(@Query("category") category: String):Response<ImagesCategory>
}