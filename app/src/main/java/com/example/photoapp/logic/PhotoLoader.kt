package com.example.photoapp.logic

import com.example.photoapp.model.PhotoModel

class PhotoLoader {

     fun loadPhoto(url:String,title:String,date:String) : PhotoModel{
         val model = PhotoModel(url,title,date)
         return model
    }


}