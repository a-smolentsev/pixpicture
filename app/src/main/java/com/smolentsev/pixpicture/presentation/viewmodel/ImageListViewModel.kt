package com.smolentsev.pixpicture.presentation.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.lifecycle.*
import com.smolentsev.pixpicture.ImageApp
import com.smolentsev.pixpicture.constants.Companion.LOADING
import com.smolentsev.pixpicture.constants.Companion.SUCCESS_LOAD
import com.smolentsev.pixpicture.data.Hit
import com.smolentsev.pixpicture.data.RetrofitInstance
import kotlinx.coroutines.launch

class ImageListViewModel(app: Application) : AndroidViewModel(app) {
    private val _image = MutableLiveData<List<Hit>>()
    val image: LiveData<List<Hit>>
        get() = _image
    private val _stateLoading = MutableLiveData<Int>()
    val stateLoading: LiveData<Int>
        get() = _stateLoading


    fun getImage(category: String) {
        viewModelScope.launch {
            _stateLoading.value = LOADING
            val result = RetrofitInstance.api.getImage(category.lowercase())
            if (result.isSuccessful) {
                _image.value = result.body()!!.hits
                _stateLoading.value = SUCCESS_LOAD
                Log.d("Response: ", _image.value?.size.toString())
                Log.d("response: ", result.isSuccessful.toString())
            } else Log.d("response: ", result.errorBody().toString())

        }
    }

    private fun hasInternetCheck():Boolean{
        val connectivityManger = getApplication<ImageApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManger.activeNetwork ?: return false
        val capabilities = connectivityManger.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}