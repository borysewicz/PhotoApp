package com.example.photoapp.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel

class PhotoDataFragment : Fragment() {

    companion object {
        private const val MODEL_KEY = "model"
        fun newInstance(model:PhotoModel) : PhotoDataFragment{
            val bundle = Bundle()
            bundle.putSerializable(MODEL_KEY,model)
            val fragment = PhotoDataFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.photo_data_fragment,container,false)
        if (arguments != null){
            inflateTextViews(view)
        }
        return view

    }

    private fun inflateTextViews(view: View){
        val model = arguments?.getSerializable(MODEL_KEY) as PhotoModel
        view.findViewById<TextView>(R.id.data_fragment_photo_title).text = model.title
        view.findViewById<TextView>(R.id.data_fragment_photo_date).text = model.Date
        view.findViewById<TextView>(R.id.data_fragment_photo_tags).text = model.tags.joinToString (", ")
        view.findViewById<TextView>(R.id.data_fragment_photo_url).text = model.url
    }
}