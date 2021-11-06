package com.example.vinilos.ui.createAlbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.vinilos.R

class CreateAlbum : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_album)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.activity_create_album_tittle);
        actionbar.setDisplayHomeAsUpEnabled(true)

        val spinnerGenero: Spinner = findViewById(R.id.spinnerAlbumGenero)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.opciones_genero,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerGenero.adapter = adapter
        }

        val spinnerDisquera: Spinner = findViewById(R.id.spinnerAlbumDisquera)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.opciones_disquera,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerDisquera.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun cancelCreation(view: View) {
        this.onBackPressed();
    }
}