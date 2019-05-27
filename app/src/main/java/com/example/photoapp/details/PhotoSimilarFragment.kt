package com.example.photoapp.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.mainrecyclerview.PhotoAdapter.Companion.MODEL_KEY
import com.example.photoapp.mainrecyclerview.PhotoAdapter.Companion.MODEL_LIST
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class PhotoSimilarFragment : Fragment(){

    companion object {
        val SIMILAR_PHOTOS_SHOWN = 6
        fun newInstance(modelList : String, model :PhotoModel) : PhotoSimilarFragment{
            val bundle = Bundle()
            bundle.putString(MODEL_LIST,modelList)
            bundle.putSerializable(MODEL_KEY,model)
            val fragment = PhotoSimilarFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    private lateinit var photos : List<PhotoModel>
    private lateinit var imgViewsIds : List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photos = getSimilarImages()
        imgViewsIds = getImgViewsIds()
    }

    private fun getImgViewsIds(): List<Int> {
        return listOf(R.id.similar_IV1,R.id.similar_IV2,R.id.similar_IV3,R.id.similar_IV4,R.id.similar_IV5,R.id.similar_IV6)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.photo_similar_fragment,container,false)
        loadPhotos(view)
        return view
    }

    private fun loadPhotos(view: View?) {
        for ((index,photo) in photos.withIndex()){
            val imgView = view?.findViewById<ImageView>(imgViewsIds[index])
            Picasso.get().load(photo.url).into(imgView)
        }
    }


    private fun getSimilarImages() : List<PhotoModel>{
        val imageList = getImagesFromJson()
        val model = arguments?.getSerializable(MODEL_KEY) as PhotoModel
        val photoSimilarityList  = countSimilarity(imageList,model).sortedBy { photoSimilarPair -> photoSimilarPair.second}
                                                                .map { photoSimilarPair -> photoSimilarPair.first }
        return if (photoSimilarityList.size < SIMILAR_PHOTOS_SHOWN){
            photoSimilarityList
        } else photoSimilarityList.take(SIMILAR_PHOTOS_SHOWN)
    }

    private fun getImagesFromJson(): List<PhotoModel> {
        val listJson = arguments?.getString(MODEL_LIST)
        val loadedPhotos = Gson().fromJson(listJson, Array<PhotoModel>::class.java)
        return loadedPhotos.toList()
    }

    private fun countSimilarity(imageList : List<PhotoModel>, model: PhotoModel) : MutableList<Pair<PhotoModel,Int>>{
        val similarities = mutableListOf<Pair<PhotoModel,Int>>()
        for (photo in imageList.filter { p -> p.url != model.url }){
            var similarity = 0
            for (tag in photo.tags){
                if (model.tags.contains(tag)){
                    similarity++
                }
            }
            if (similarity >0){
                similarities.add(Pair(photo,similarity))
            }
        }
        return similarities
    }


}