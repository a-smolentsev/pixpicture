package com.smolentsev.pixpicture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.domain.repository.RepositoryApp

object RepositoryAppImpl:RepositoryApp {
    private var categoryListLD = MutableLiveData<List<Category>>()
    private var categoryList = mutableListOf<Category>()

    init {
        val categoryLis = mutableListOf(Category(0,"Nature"),
        Category(1,"Animals"),
        Category(2,"Backgrounds"),
        Category(3,"Fashion"),
        Category(4,"Industry"),
        Category(5,"Sports"),
        Category(6,"Travel"),
        Category(7,"Music"))
        categoryList=categoryLis
        categoryListLD.value=categoryLis.toList()
    }
    override fun getCategoryList(): LiveData<List<Category>> {
        return categoryListLD
    }
    private fun updateCategory(){
        categoryListLD.value=categoryList.toList()
    }
}

