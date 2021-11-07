package com.example.vinilos.ui.createAlbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.vinilos.R
import com.google.android.material.textfield.TextInputEditText

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
            R.layout.my_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.my_spinner_drawable)
            // Apply the adapter to the spinner
            spinnerGenero.adapter = adapter
        }

        val spinnerDisquera: Spinner = findViewById(R.id.spinnerAlbumDisquera)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.opciones_disquera,
            R.layout.my_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.my_spinner_drawable)
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

    fun createAlbum(view: View) {
        val albumName : EditText = findViewById(R.id.editTextAlbumName)
        val albumDescription : EditText = findViewById(R.id.editTextAlbumDescripcion)
        val albumFecha : EditText = findViewById(R.id.editTextAlbumFecha)
        val spinnerDisquera: Spinner = findViewById(R.id.spinnerAlbumDisquera)
        val spinnerGenero: Spinner = findViewById(R.id.spinnerAlbumGenero)


    }
}