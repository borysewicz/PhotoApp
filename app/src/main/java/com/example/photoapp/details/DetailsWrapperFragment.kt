package com.example.photoapp.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.recyclerview.PhotoAdapter.Companion.MODEL_KEY
import com.example.photoapp.recyclerview.PhotoAdapter.Companion.MODEL_LIST

class DetailsWrapperFragment : Fragment() {
    companion object {
        fun newInstance(model: PhotoModel, modelList:String) : DetailsWrapperFragment{
            val bundle = Bundle()
            bundle.putSerializable(MODEL_KEY, model)
            bundle.putString(MODEL_LIST,modelList)
            val fragment = DetailsWrapperFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
       return  inflater.inflate(R.layout.details_wrapper_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null){
            inflateChildFragments()
        }
    }

    private fun inflateChildFragments() {
        val model = arguments?.getSerializable(MODEL_KEY) as PhotoModel
        val dataFragment = PhotoDataFragment.newInstance(model)
        val similarFragment = PhotoSimilarFragment.newInstance(arguments?.getString(MODEL_LIST) ?: "", model)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.details_dataF,dataFragment)
        transaction.replace(R.id.details_similarF,similarFragment)
        transaction.commit()
    }
}