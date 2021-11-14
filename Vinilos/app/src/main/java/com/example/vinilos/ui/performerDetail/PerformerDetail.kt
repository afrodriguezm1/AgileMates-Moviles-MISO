package com.example.vinilos.ui.performerDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer
import com.example.vinilos.ui.album.AlbumsAdapter
import com.example.vinilos.ui.artistas.PerformerAdapter
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.PerformerViewModel

class PerformerDetail : AppCompatActivity() {

    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.performer_detail)

        val actionbar = supportActionBar
        actionbar!!.title = "";
        actionbar.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            PerformerViewModel.Factory(application)
        ).get(PerformerViewModel::class.java)

        viewModel.performerDetail.observe(this, Observer<Performer> {
            it.apply {
                //viewModel.refreshDetailPerformerFromNetwork()
            }
        })


        val textViewPerformerName : TextView = findViewById(R.id.textViewPerformerName)
        val textViewCreationDate : TextView = findViewById(R.id.textViewCreationDate)
        val textViewDescription : TextView = findViewById(R.id.textViewDescription)

        val bundle : Bundle?= intent.extras
        val performerType =  bundle!!.getString("performerType")
        val id =  bundle?.getInt("performerId")

        textViewPerformerName.text = "Bony Casas $id"
        textViewCreationDate.text = "Bony Casas $id"
        textViewDescription.text = "Bony Casas $id"

    }
}