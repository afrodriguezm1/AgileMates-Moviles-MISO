package com.example.vinilos.ui.createAlbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.vinilos.R
import com.example.vinilos.models.Album
import com.example.vinilos.viewmodels.AlbumViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.ui.album.AlbumsAdapter

class CreateAlbum : AppCompatActivity() {

    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

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

        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(application)
        ).get(AlbumViewModel::class.java)

        viewModel.album.observe(this, Observer<Album> {
            it.apply {
                viewModel.refreshDataCreateFromNetwork()
                cancelCreation();
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun cancelCreation() {
        this.onBackPressed();
    }

    fun createAlbum(view: View) {
        val albumName : EditText = findViewById(R.id.editTextAlbumName)
        val albumDescription : EditText = findViewById(R.id.editTextAlbumDescripcion)
        val albumReleaseDate : EditText = findViewById(R.id.editTextAlbumFecha)
        val spinnerRecorLabel: Spinner = findViewById(R.id.spinnerAlbumDisquera)
        val spinnerGenre: Spinner = findViewById(R.id.spinnerAlbumGenero)
        val cover : EditText = findViewById(R.id.editTextAlbumPortada)

        val albumView = Album(
            id = 0,
            name = albumName.text.toString(),
            cover= cover.text.toString(),
            releaseDate= albumReleaseDate.text.toString(),
            description=albumDescription.text.toString(),
            genre= spinnerGenre.selectedItem.toString(),
            recordLabel= spinnerRecorLabel.selectedItem.toString()
        )

        viewModel.setAlbum(albumView)
    }
}

