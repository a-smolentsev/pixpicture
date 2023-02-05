package com.smolentsev.pixpicture.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smolentsev.pixpicture.domain.ImageApp
import com.smolentsev.pixpicture.domain.entity.Hit
import com.smolentsev.pixpicture.domain.entity.ImagesCategory
import com.smolentsev.pixpicture.domain.repository.RepositoryImages
import com.smolentsev.pixpicture.presentation.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ImageListViewModel(app: Application, val imagesRepo: RepositoryImages) :
    AndroidViewModel(app) {
    private val _image = MutableLiveData<List<Hit>>()
    val allImages: MutableLiveData<Resource<ImagesCategory>> = MutableLiveData()

    var page = 1
    var allImagesResponse: ImagesCategory? = null

    fun getImage(category: String) = viewModelScope.launch {
        safeCallGetImages(category)
    }


    private fun checkResponse(response: Response<ImagesCategory>): Resource<ImagesCategory> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                page++
                if (allImagesResponse == null) {
                    allImagesResponse = resultResponse
                } else {
                    val oldArticles = allImagesResponse?.hits
                    val newArticles = resultResponse.hits
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(allImagesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())

    }


    private suspend fun safeCallGetImages(category: String) {
        allImages.postValue(Resource.Loading())
        try {
            if (hasInternetCheck()) {
                val response = imagesRepo.getImagesByCategory(category, page)
                allImages.postValue(checkResponse(response))
            } else {
                allImages.postValue(Resource.Error("Нет подключения к интернету"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> allImages.postValue(Resource.Error("Ошибка соединения"))
                else -> allImages.postValue(Resource.Error("Ошибка соединения"))
            }
        }

    }

    private fun hasInternetCheck(): Boolean {
        val connectivityManager = getApplication<ImageApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}