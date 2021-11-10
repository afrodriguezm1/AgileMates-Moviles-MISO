package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (private val application: Application){

    suspend fun refreshAlbumList(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    fun refreshAlbumCreate(album:Album) {

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