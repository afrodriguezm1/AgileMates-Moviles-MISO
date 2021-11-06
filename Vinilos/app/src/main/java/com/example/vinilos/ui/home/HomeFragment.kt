package com.example.vinilos.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.vinilos.databinding.FragmentHomeBinding
import com.example.vinilos.models.Album
import com.example.vinilos.viewmodels.AlbumViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var albumViewMode: AlbumViewModel

    private lateinit var albumList : ArrayList<Album>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        albumList = ArrayList()
        albumList.add(Album(1,"Bad", "https://m.media-amazon.com/images/I/6148jblbNqL._SL1500_.jpg", "1987", "Bad es el séptimo álbum de estudio del cantante estadounidense Michael Jackson,", "Pop", "Michael Jackson"))
        albumList.add(Album(2,"Thriller", "https://m.media-amazon.com/images/I/71Y24VlCZGL._SL1500_.jpg", "1982", "Thriller es el sexto álbum de estudio del artista estadounidense Michael Jackson", "Pop", "Michael Jackson"))
        albumList.add(Album(3,"Off the Wall", "https://m.media-amazon.com/images/I/81BXhwCtiKL._SL1500_.jpg", "1979", "Off the Wall es el quinto álbum de estudio solista de Michael Jackson editado en 1979", "Pop", "Michael Jackson"))
        albumList.add(Album(4,"Dangerous", "https://img.europapress.es/fotoweb/fotonoticia_20161126095208_1200.jpg", "1991", "Dangerous es el octavo álbum de estudio del cantante estadounidense Michael Jackson,", "Pop", "Michael Jackson"))
        albumList.add(Album(5,"Ben", "https://m.media-amazon.com/images/I/51FxxrmpPKL.jpg", "1972", "Ben es el segundo álbum de Michael Jackson a los 13 años de edad,", "Pop", "Michael Jackson"))
        albumList.add(Album(6,"Invincible", "https://m.media-amazon.com/images/I/71TXrGXF8TL._SL1500_.jpg", "2001", "nvincible es el décimo y último álbum de estudio del cantante estadounidense Michael Jackson", "Pop", "Michael Jackson"))
        albumList.add(Album(7,"Got to Be There", "https://m.media-amazon.com/images/I/51Zyd1d+SmL.jpg", "1972", "Got to Be There es el primer álbum de estudio como solista con el que Michael Jackson debutó en 1972,", "Pop", "Michael Jackson"))
        albumList.add(Album(8,"Bad 8", "", "2007", "Album", "Pop", "Michael Jackson"))

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

