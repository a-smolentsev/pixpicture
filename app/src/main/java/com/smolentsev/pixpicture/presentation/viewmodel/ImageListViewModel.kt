package com.smolentsev.pixpicture.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smolentsev.pixpicture.data.Hit
import com.smolentsev.pixpicture.data.ImageApi
import com.smolentsev.pixpicture.data.ImagesCategory
import com.smolentsev.pixpicture.data.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ImageListViewModel: ViewModel() {
    private val _image = MutableLiveData<List<Hit>>()
    val image: LiveData<List<Hit>>
        get() = _image
        fun getImage(category:String){
        viewModelScope.launch {

            val result = RetrofitInstance.api.getImage(category.toLowerCase())
            if(result.isSuccessful){
                _image.value=result.body()!!.hits
                Log.d("Response: ", _image.value?.size.toString())
                    Log.d("response: ", result.isSuccessful.toString())
            }
            else Log.d("response: ",result.errorBody().toString())

        }
    }
}