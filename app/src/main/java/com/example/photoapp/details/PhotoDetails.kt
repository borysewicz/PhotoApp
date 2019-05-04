package com.example.photoapp.details

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.recyclerview.PhotoAdapter.Companion.MODEL_KEY


class PhotoDetails : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        addPhotoFullFragment()
    }

    private fun addPhotoFullFragment(){
        val fragManager = supportFragmentManager
        val trans =  fragManager.beginTransaction()
        val fragment = PhotoFullFragment.newInstance((intent.getSerializableExtra(MODEL_KEY) as PhotoModel).url)
        trans.add(R.id.fragment,fragment)
        trans.commit()
    }
}
