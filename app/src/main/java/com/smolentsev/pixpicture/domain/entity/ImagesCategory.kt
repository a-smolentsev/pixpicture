package com.smolentsev.pixpicture.domain.entity


data class ImagesCategory(
    val hits:  MutableList<Hit>,
    val total: Int,
    val totalHits: Int
)