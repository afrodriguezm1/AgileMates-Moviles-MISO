package com.example.vinilos.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.vinilos.databinding.FragmentHomeBinding
import com.example.vinilos.models.Album

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var albumList : ArrayList<Album>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        albumList = ArrayList()
        albumList.add(Album(1,"Bad", "Michael Jackson", "2000", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(2,"Bad 2", "Michael Jackson", "2001", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(3,"Bad 3", "Michael Jackson", "2002", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(4,"Bad 4", "Michael Jackson", "2003", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(5,"Bad 5", "Michael Jackson", "2004", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(6,"Bad 6", "Michael Jackson", "2005", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(7,"Bad 7", "Michael Jackson", "2006", "Album", "Pop", "Michael Jackson"))
        albumList.add(Album(8,"Bad 8", "Michael Jackson", "2007", "Album", "Pop", "Michael Jackson"))

        binding.lvAlbum.adapter = getActivity()?.let { AlbumAdapter(it, albumList) }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}

