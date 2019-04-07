package com.example.photoapp.logic

import android.graphics.Bitmap
import com.example.photoapp.model.PhotoModel
import com.squareup.picasso.Picasso

class PhotoLoader {

     fun loadPhoto(url:String,title:String,date:String) : PhotoModel{
      //  val img: Bitmap  = Picasso.get().load(url).get()
         val model = PhotoModel(url,title,date, emptyArray())
         return model
    }


}