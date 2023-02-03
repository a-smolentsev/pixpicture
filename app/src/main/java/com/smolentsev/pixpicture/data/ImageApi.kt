package com.smolentsev.pixpicture.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("/api/?key=33290131-c6f24100384c898ac3153e48c&per_page=200")
    suspend fun getImage(@Query("category") category: String):Response<ImagesCategory>
}