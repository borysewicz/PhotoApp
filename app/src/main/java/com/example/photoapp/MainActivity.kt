package com.example.photoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.photoapp.logic.PhotoLoader
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.recyclerview.SwipeToDeleteCallback
import com.example.photoapp.recyclerview.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val CARD_MARGIN = 16
        const val ADD_PHOTO = 997

        const val titleKey = "title"
        const val dateKey = "date"
        const val urlKey = "url"
    }

    private val imageList : MutableList<PhotoModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.photoapp_toolbar))

        val manager = LinearLayoutManager(this)
        val photoAdapter = PhotoAdapter(imageList)
        val recycler = findViewById<RecyclerView>(R.id.photoapp_main_mainRV).apply{
            setHasFixedSize(false)
            layoutManager = manager
            adapter = photoAdapter
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(this))
            itemTouchHelper.attachToRecyclerView(this)
        }
        addRecyclerDecoration(recycler)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.photoapp_menu_addPhoto -> startAddPhotoActivity()
        }
        return false
    }

    private fun startAddPhotoActivity() : Boolean{
        val intent = Intent(this,AddPhoto::class.java)
        startActivityForResult(intent, ADD_PHOTO)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            ADD_PHOTO ->{
                if (resultCode == Activity.RESULT_OK){
                    val url = data?.getStringExtra(urlKey)
                    val title = data?.getStringExtra(titleKey)
                    val date = data?.getStringExtra(dateKey)
                    imageList.add(PhotoLoader().loadPhoto(url!!,title!!,date!!))
                    photoapp_main_mainRV.adapter?.notifyDataSetChanged()
                }
            }
        }
    }


    private fun addRecyclerDecoration(recycler:RecyclerView){
        recycler.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = MainActivity.CARD_MARGIN
                outRect.right = MainActivity.CARD_MARGIN
                outRect.bottom = MainActivity.CARD_MARGIN

                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = MainActivity.CARD_MARGIN
                }
            }
        })
    }

}
