package com.example.photoapp.recyclerview

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.photoapp.R
import java.util.*

class PhotoAdapter :  RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

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
        return 1
    }

    override fun onBindViewHolder(photoView: PhotoViewHolder, pos: Int) {
        photoView.date.text = Calendar.getInstance().time.toString()
        photoView.tags.text = "Kotek piesek mysz"
        photoView.title.text = "Dupa"
    }


}