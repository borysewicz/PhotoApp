package com.example.photoapp.model

import java.io.Serializable


class PhotoModel(val url: String,val title: String, val Date: String) : Serializable{
      var tags: List<String> = listOf()
}