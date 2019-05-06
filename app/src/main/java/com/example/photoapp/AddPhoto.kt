package com.example.photoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_add_photo.*
import kotlinx.android.synthetic.main.content_add_photo.*
import java.text.SimpleDateFormat
import java.util.*

class AddPhoto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        setSupportActionBar(toolbar)
        setupButton()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupButton(){
        photoapp_addp_button.setOnClickListener {
            val url = getValueFromEditText(photoapp_addp_urlET)
            val title = getValueFromEditText(photoapp_addp_nameET)
            if (url == "" || title == "") return@setOnClickListener
            val date = getCurrentDate()
            exit(url,title,date)
        }
    }

    private fun exit(url: String, title: String, date: String) {
        val intent = Intent()
        intent.putExtra(MainActivity.URLKEY,url)
        intent.putExtra(MainActivity.TITLEKEY,title)
        intent.putExtra(MainActivity.DATEKEY,date)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }


    private fun getValueFromEditText(editText: EditText?): String {
        if (editText?.text.toString() == ""){
            editText?.error = getString(R.string.error_empty)
            return ""
        }
        return editText?.text.toString()
    }

    private fun getCurrentDate() : String{
        val time = Calendar.getInstance().time

        val timeFormat = SimpleDateFormat(getString(R.string.photoapp_date_format), Locale.getDefault())
        return timeFormat.format(time)
    }

}
