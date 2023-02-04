package com.smolentsev.pixpicture.domain

import com.smolentsev.pixpicture.R

object CategoryImageSet {
    var image: Int = 0
    fun imageSet(id: Int): Int{
        when(id){
            0-> image = R.drawable.nature
            1-> image =R.drawable.animals
            2-> image =R.drawable.background
            3-> image =R.drawable.fashion
            4-> image =R.drawable.industry
            5-> image =R.drawable.sports
            6-> image =R.drawable.travel
            7-> image =R.drawable.concert
        }
        return image
    }
}