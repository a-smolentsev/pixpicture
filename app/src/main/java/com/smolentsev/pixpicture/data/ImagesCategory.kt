package com.smolentsev.pixpicture.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class ImagesCategory(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)