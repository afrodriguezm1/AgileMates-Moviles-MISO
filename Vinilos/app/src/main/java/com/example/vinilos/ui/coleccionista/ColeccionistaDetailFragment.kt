package com.example.vinilos.ui.coleccionista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.databinding.FragmentColeccionistaDetailBinding
import com.example.vinilos.viewmodels.CollectorDetailViewModel

class ColeccionistaDetailFragment : Fragment() {
    private var _binding: FragmentColeccionistaDetailBinding? = null
    private val binding get() = _binding!!
/*    private lateinit var recyclerView: RecyclerView*/
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

        viewModel = ViewModelProvider(
            this,
            CollectorDetailViewModel.Factory(activity.application, args.collectorId)
        ).get(CollectorDetailViewModel::class.java)

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