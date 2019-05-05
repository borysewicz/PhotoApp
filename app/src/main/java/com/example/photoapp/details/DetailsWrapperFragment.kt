package com.example.photoapp.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel

class DetailsWrapperFragment : Fragment() {
    companion object {
        private const val MODEL_KEY = "model"
        fun newInstance(model: PhotoModel) : DetailsWrapperFragment{
            val bundle = Bundle()
            bundle.putSerializable(MODEL_KEY, model)
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
        val dataFragment = PhotoDataFragment.newInstance(arguments?.getSerializable(MODEL_KEY) as PhotoModel)
        val similarFragment = PhotoSimilarFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.details_dataF,dataFragment)
        transaction.replace(R.id.details_similarF,similarFragment)
        transaction.commit()
    }
}