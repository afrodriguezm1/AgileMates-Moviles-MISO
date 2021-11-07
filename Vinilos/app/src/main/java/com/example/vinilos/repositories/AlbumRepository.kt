package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (private val application: Application){

    fun refreshAlbumList(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }

    fun refreshAlbumCreate(album:Album,callback: (Album)->Unit, onError: (VolleyError)->Unit) {

        val albumJSONObject = JSONObject()
        albumJSONObject.put("name",album.name)
        albumJSONObject.put("cover",album.cover)
        albumJSONObject.put("releaseDate",album.releaseDate)
        albumJSONObject.put("description",album.description)
        albumJSONObject.put("genre",album.genre)
        albumJSONObject.put("recordLabel",album.recordLabel)

        NetworkServiceAdapter.getInstance(application).postAlbum(albumJSONObject)
    }
}