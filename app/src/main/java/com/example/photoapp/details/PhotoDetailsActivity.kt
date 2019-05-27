package com.example.photoapp.details

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.example.photoapp.R
import com.example.photoapp.model.PhotoModel
import com.example.photoapp.mainrecyclerview.PhotoAdapter.Companion.MODEL_KEY
import com.example.photoapp.mainrecyclerview.PhotoAdapter.Companion.MODEL_LIST


class PhotoDetailsActivity : FragmentActivity() {

    companion object {
        private const val PAGES = 2
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

        override fun getItem(position: Int): Fragment {
            val model = intent.getSerializableExtra(MODEL_KEY) as PhotoModel
            return when(position){
                0 -> PhotoFullFragment.newInstance(model.url)
                else -> DetailsWrapperFragment.newInstance(model, intent.getStringExtra(MODEL_LIST))
            }
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 ->   getString(R.string.details_full)
                else -> getString(R.string.details_data)
            }
        }
    }

}
