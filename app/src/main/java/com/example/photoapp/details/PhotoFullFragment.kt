package com.example.photoapp.details

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.squareup.picasso.Picasso


class PhotoFullFragment : Fragment() {

    companion object {
        val URL = "url"
        fun newInstance(url:String) : PhotoFullFragment{
            val bundle = Bundle()
            bundle.putString(URL,url)
            val fragment = PhotoFullFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.photo_full_fragment, container, false)
        if (arguments != null){
            val imgUrl = arguments!!.getString(URL)
            setImage(imgUrl,view)
        }
        return view
    }

    private fun setImage(url: String, view: View) {
        Picasso.get().load(url).into(view.findViewById<ImageView>(R.id.image_full_fragmentIV))
    }
}
