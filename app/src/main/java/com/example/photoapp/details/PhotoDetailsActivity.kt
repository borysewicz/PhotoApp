package com.example.photoapp.details

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.widget.TableLayout
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.recyclerview.PhotoAdapter.Companion.MODEL_KEY


class PhotoDetailsActivity : FragmentActivity() {

    companion object {
        private val PAGES = 1
        private val PHOTO_FULL = "Full photo"
    }
    private lateinit var pager: ViewPager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        pager = findViewById(R.id.photo_details_VP)
        pager.adapter = ScreenSlidePagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.photo_details_TL)
        tabLayout.setupWithViewPager(pager)
    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = PAGES

        override fun getItem(position: Int): Fragment =
            PhotoFullFragment.newInstance((intent.getSerializableExtra(MODEL_KEY) as PhotoModel).url)

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0 ->  return PHOTO_FULL
            }
            return "Error"
        }
    }

}
