package com.example.vinilos.ui.artistas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentArtistasBinding
import com.example.vinilos.models.Performer
import com.example.vinilos.ui.createAlbum.CreateAlbum
import com.example.vinilos.ui.performerDetail.PerformerDetail
import com.example.vinilos.viewmodels.PerformerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ArtistasFragment : Fragment() {

    private var _binding: FragmentArtistasBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistasBinding.inflate(inflater, container, false)
        val view: View = binding.root
        viewModelAdapter = PerformerAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.performersRv
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModelAdapter?.setOnItemClickListener(object: PerformerAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                var performer  = viewModel.performers.value?.get(position)
                //Toast.makeText(this@ArtistasFragment.context,"click en ${performer?.id} de tipo ${performer?.performerType}", Toast.LENGTH_SHORT).show()
                var intent = Intent(this@ArtistasFragment.context,PerformerDetail::class.java)
                intent.putExtra("performerId",performer?.performerId)
                intent.putExtra("performerType",performer?.performerType)
                startActivity(intent)
            }
        })

        recyclerView.adapter = viewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
        }

        viewModel = ViewModelProvider(
            this,
            PerformerViewModel.Factory(activity.application)
        ).get(PerformerViewModel::class.java)

        viewModel.performers.observe(viewLifecycleOwner, Observer<List<Performer>> {
            it.apply {
                viewModelAdapter!!.performers = this
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
}