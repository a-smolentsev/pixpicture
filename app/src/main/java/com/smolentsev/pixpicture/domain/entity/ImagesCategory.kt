package com.smolentsev.pixpicture.domain.entity


data class ImagesCategory(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)