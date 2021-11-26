package com.example.vinilos.ui.coleccionista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentColeccionistaDetailBinding
import com.example.vinilos.models.Collector
import com.example.vinilos.models.Performer
import com.example.vinilos.viewmodels.CollectorDetailViewModel
import com.squareup.picasso.Picasso

class ColeccionistaDetailFragment : Fragment() {
    private var _binding: FragmentColeccionistaDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentColeccionistaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: ColeccionistaDetailFragmentArgs by navArgs()
        val collectorName = binding.textViewCollectorName
        val albumCollector = binding.albumCollectorImg
        val layoutAlbumCollector = binding.containerCollectorAlbumList
        val performerCollector = binding.performerCollectorImg
        val layoutPerformerCollector = binding.containerCollectorPerformerList

        viewModel = ViewModelProvider(
            this,
            CollectorDetailViewModel.Factory(activity.application, args.collectorId)
        ).get(CollectorDetailViewModel::class.java)

        viewModel.collector.observe(this, Observer<Collector> {
            it.apply {
                val collector = viewModel.collector.value
                collectorName.text = collector?.name
                collectorName.visibility = VISIBLE

                val scale = resources.displayMetrics.density
                val dpWidthInPx = (200 * scale).toInt()

                collector?.albums?.forEach { album ->
                    val imageView = ImageView(albumCollector.context)
                    imageView.layoutParams = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    val lp = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    lp.setMargins(0, 0, 50, 0)
                    imageView.layoutParams = lp;
                    imageView.setOnClickListener {

                    }
                    layoutAlbumCollector?.addView(imageView)
                    loadImage(album.cover, imageView)
                }

                collector?.performers?.forEach { performer ->
                    val imageView = ImageView(performerCollector.context)
                    imageView.layoutParams = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    val lp = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    lp.setMargins(0, 0, 50, 0)
                    imageView.layoutParams = lp;
                    layoutPerformerCollector?.addView(imageView)
                    loadImage(performer.image, imageView)
                }
            }
        })

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun loadImage(image:String?,control:ImageView){
        try{
            Picasso.get()
                .load(image)
                .error(R.mipmap.ic_launcher_round)
                .into(control)
        }catch (e: Exception) { }
    }
}