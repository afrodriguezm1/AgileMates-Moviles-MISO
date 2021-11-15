package com.example.vinilos.ui.performerDetail

import android.R.attr
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.vinilos.R
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer
import com.example.vinilos.models.PerformerType
import com.example.vinilos.ui.artistas.PerformerAdapter
import com.example.vinilos.viewmodels.PerformerViewModel
import com.squareup.picasso.Picasso
import android.view.ViewGroup
import androidx.core.view.marginRight
import android.R.attr.right

import android.R.attr.left





class PerformerDetail : AppCompatActivity() {

    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.performer_detail)

        val textViewPerformerName : TextView = findViewById(R.id.textViewPerformerName)
        val textViewCreationDate : TextView = findViewById(R.id.textViewCreationDate)
        val textViewDescription : TextView = findViewById(R.id.textViewDescription)
        val imageViewPerformer : ImageView = findViewById(R.id.imagePerformer)
        val albumPerformerImg: ImageView = findViewById(R.id.albumPerformerImg)

        val actionbar = supportActionBar
        actionbar!!.title = "";
        actionbar.setDisplayHomeAsUpEnabled(true)


        viewModel = ViewModelProvider(
            this,
            PerformerViewModel.Factory(application)
        ).get(PerformerViewModel::class.java)

        viewModel.performerId.observe(this, Observer<Int> {
            it.apply {
                viewModel.refreshDetailPerformerFromNetwork()
            }
        })

        viewModel.performerDetail.observe(this, Observer<Performer> {
            it.apply {
                val performer =  viewModel.performerDetail.value
                textViewPerformerName.text = performer?.name;
                textViewCreationDate.text = performer?.date;
                textViewDescription.text = performer?.description;
                loadImage(performer?.image,imageViewPerformer)

                val scale = resources.displayMetrics.density
                val dpWidthInPx = (200 * scale).toInt()

                performer?.albums?.forEach {
                    val imageView = ImageView(albumPerformerImg.context)
                    imageView.layoutParams= LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    val lp = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    lp.setMargins(0, 0,25,0)
                    imageView.setLayoutParams(lp);
                    val layout = findViewById<LinearLayout>(R.id.containerPerformerAlbumList)
                    layout?.addView(imageView)
                    loadImage(it.cover,imageView)
                }
            }
        })

        val bundle : Bundle?= intent.extras
        val performerType =  bundle!!.getString("performerType")
        val performerId =  bundle?.getInt("performerId")

        if (performerType != null) {
            viewModel.setPerformerDetailId(performerId, performerType)
        }

    }

    fun loadImage(image:String?,control:ImageView){
        try{
            Picasso.get()
                .load(image)
                .error(R.mipmap.ic_launcher_round)
                .into(control)
        }catch (e: Exception) { }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}