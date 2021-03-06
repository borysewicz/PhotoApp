package com.example.photoapp.mainrecyclerview

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.squareup.picasso.Picasso
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import android.graphics.drawable.Drawable
import com.example.photoapp.details.PhotoDetailsActivity
import com.google.gson.Gson


class PhotoAdapter(val imageList: MutableList<PhotoModel>) :  RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    companion object {
        val MODEL_KEY = "PhotoModel"
        val MODEL_LIST = "list_of_models"
        val TAG_ERROR = "Error loading tag"
        val TAGS_VISIBLE = 3
    }

    class PhotoViewHolder(cardView: CardView, val title : TextView, val image : ImageView
                            , val date : TextView, val tags: TextView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val card : CardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_card,parent,false) as CardView

        return PhotoViewHolder(card,card.findViewById(R.id.photoapp_card_titleTV),
                                card.findViewById(R.id.photoapp_card_photoIV),card.findViewById(R.id.photoapp_card_dateTV),
                                card.findViewById(R.id.photoapp_card_tagsTV))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(photoView: PhotoViewHolder, pos: Int) {
        val model = imageList[pos]
        photoView.date.text = model.Date
        photoView.title.text = model.title
        Picasso.get().load(model.url).into(object: com.squareup.picasso.Target {
            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
            }
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                photoView.image.setImageBitmap(bitmap)
               if (model.tags == listOf<String>()){  // if the photo hasn't been tagged yet, tag it, else use existing tags
                   setTags(bitmap, model, photoView)
               }
                photoView.tags.text = model.tags.take(TAGS_VISIBLE).joinToString (", ")
            }
        })
        setOnClickListener(photoView,model)
    }

    private fun setOnClickListener(photoView: PhotoViewHolder,model: PhotoModel) {
        photoView.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context,PhotoDetailsActivity::class.java)
            intent.putExtra(MODEL_KEY,model)
            val imagesAsJson = Gson().toJson(imageList)
            intent.putExtra(MODEL_LIST,imagesAsJson)
            context.startActivity(intent)
        }
    }

    private fun setTags(img : Bitmap?, model: PhotoModel, photoView: PhotoViewHolder) {
        val firebaseImg = FirebaseVisionImage.fromBitmap(img!!)
        val labeler =FirebaseVision.getInstance().onDeviceImageLabeler
        labeler.processImage(firebaseImg).addOnSuccessListener { labels->
            val res = labels.map{it.text}
            model.tags = res
            photoView.tags.text = model.tags.take(TAGS_VISIBLE).joinToString (", ")
        }.addOnFailureListener{e ->
            Log.e(TAG_ERROR,e.toString())
        }
    }

    fun removeAt(pos : Int){
        imageList.removeAt(pos)
        notifyItemRemoved(pos)
    }


}