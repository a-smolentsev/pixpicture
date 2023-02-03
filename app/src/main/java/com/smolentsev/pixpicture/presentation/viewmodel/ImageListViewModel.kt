package com.smolentsev.pixpicture.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smolentsev.pixpicture.constants.Companion.LOADING
import com.smolentsev.pixpicture.constants.Companion.SUCCESS_LOAD
import com.smolentsev.pixpicture.data.Hit
import com.smolentsev.pixpicture.data.RetrofitInstance
import kotlinx.coroutines.launch

class ImageListViewModel : ViewModel() {
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

}