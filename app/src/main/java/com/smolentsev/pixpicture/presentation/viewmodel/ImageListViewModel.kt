package com.smolentsev.pixpicture.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.lifecycle.*
import com.smolentsev.pixpicture.ImageApp
import com.smolentsev.pixpicture.constants.Companion.FAIL_LOAD
import com.smolentsev.pixpicture.constants.Companion.LOADING
import com.smolentsev.pixpicture.constants.Companion.SUCCESS_LOAD
import com.smolentsev.pixpicture.data.Hit
import com.smolentsev.pixpicture.data.ImagesCategory
import com.smolentsev.pixpicture.data.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ImageListViewModel(app: Application) : AndroidViewModel(app) {
    private val _image = MutableLiveData<List<Hit>>()
    val image: LiveData<List<Hit>>
        get() = _image
    private val _stateLoading = MutableLiveData<Int>()
    val stateLoading: LiveData<Int>
        get() = _stateLoading


    fun getImage(category: String) {
        viewModelScope.launch {
            safeCallGetImages(category)
        }
    }

    private fun checkResponse(response: Response<ImagesCategory>){
        _stateLoading.value = LOADING
        if(response.isSuccessful){
            _stateLoading.value = SUCCESS_LOAD
        } else{
            Log.e("Error", response.errorBody().toString())
            _stateLoading.value = FAIL_LOAD
        }
    }

    private suspend fun safeCallGetImages(category: String){
        _stateLoading.value = LOADING
       if (hasInternetCheck()){
                val result = RetrofitInstance.api.getImage(category.lowercase())
                _image.value = result.body()!!.hits
                checkResponse(result)
            } else {
                _stateLoading.value = FAIL_LOAD
                Log.e("Error: ", "нет интернета")
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