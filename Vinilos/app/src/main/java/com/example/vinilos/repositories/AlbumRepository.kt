package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (private val application: Application){

    suspend fun refreshAlbumList(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshAlbumCreate(album:Album) {
        NetworkServiceAdapter.getInstance(application).postAlbum(album)
    }
}