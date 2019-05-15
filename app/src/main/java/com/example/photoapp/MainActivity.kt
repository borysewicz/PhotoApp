package com.example.photoapp

import android.app.Activity
import android.content.Context
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
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.recyclerview.SwipeToDeleteCallback
import com.example.photoapp.recyclerview.PhotoAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val CARD_MARGIN = 16
        const val ADD_PHOTO = 997
        const val TITLEKEY = "title"
        const val DATEKEY = "date"
        const val URLKEY = "url"
        const val PREFERENCES = "preferences"
        const val PHOTOS_HISTORY = "photos_history"
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
        readPhotosFromHistory()
    }

    override fun onStart() {
        super.onStart()
        findViewById<RecyclerView>(R.id.photoapp_main_mainRV).adapter?.notifyDataSetChanged()
    }

    private fun readPhotosFromHistory(){
        val photosAsJson = getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE).getString(PHOTOS_HISTORY,"")
        if (photosAsJson.equals("")){
            return
        }
        val loadedPhotos = Gson().fromJson(photosAsJson, Array<PhotoModel>::class.java)
        imageList.addAll(0,loadedPhotos.asList())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.photoapp_menu_addPhoto -> startAddPhotoActivity()
            R.id.photoapp_menu_refresh -> findViewById<RecyclerView>(R.id.photoapp_main_mainRV).adapter?.notifyDataSetChanged()
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
                    val url = data?.getStringExtra(URLKEY)
                    val title = data?.getStringExtra(TITLEKEY)
                    val date = data?.getStringExtra(DATEKEY)
                    imageList.add(PhotoModel(url!!,title!!,date!!))
                    photoapp_main_mainRV.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val editor = getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE).edit()
        val photosAsJson = Gson().toJson(imageList)
        editor.putString(PHOTOS_HISTORY,photosAsJson)
        editor.apply()
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
