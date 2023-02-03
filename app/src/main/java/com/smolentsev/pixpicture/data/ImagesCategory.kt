package com.smolentsev.pixpicture.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesCategory(
    @SerialName("hits")
    val hits: List<Hit>,
    @SerialName("total")
    val total: Int,
    @SerialName("totalHits")
    val totalHits: Int
)