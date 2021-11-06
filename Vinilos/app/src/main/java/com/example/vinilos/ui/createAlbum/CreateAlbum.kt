package com.example.vinilos.ui.createAlbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vinilos.R

class CreateAlbum : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_album)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.activity_create_album_tittle);
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}