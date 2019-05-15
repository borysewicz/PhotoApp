package com.example.photoapp.recyclerview
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class SwipeToDeleteCallback(private val rv : RecyclerView) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewholder: RecyclerView.ViewHolder, dir: Int) {
        val adapter = rv.adapter as PhotoAdapter
        adapter.removeAt(viewholder.adapterPosition)
    }
}