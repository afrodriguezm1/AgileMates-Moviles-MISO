package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.vinilos.models.Album
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository (private val application: Application){

    suspend fun refreshAlbumList(): List<Album> {
        var resp = emptyList<Album>()
        try{
            resp = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(resp)
        }catch (e: Exception) {
            resp = CacheManager.getInstance(application.applicationContext).getAlbums()
            //Toast.makeText(application.applicationContext, "Sin conección, datos del cache", Toast.LENGTH_LONG).show()
        }
        return resp
    }

    suspend fun refreshAlbumCreate(album:Album) {
        NetworkServiceAdapter.getInstance(application).postAlbum(album)
    }

    suspend fun refreshAlbumDetail(albumId: Int): Album{
        try{
            val resp = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            CacheManager.getInstance(application.applicationContext).addAlbum(resp)
            return resp
        }catch (e: Exception) {
            return CacheManager.getInstance(application.applicationContext).getAlbum(albumId)
        }
    }
}