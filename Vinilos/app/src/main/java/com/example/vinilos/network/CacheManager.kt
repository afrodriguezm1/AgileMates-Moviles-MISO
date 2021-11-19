package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer

class CacheManager (context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context)=
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var albums: HashMap<String, List<Album>> = hashMapOf<String, List<Album>>()

    private val performers : HashMap<String, List<Performer>> = hashMapOf<String, List<Performer>>()

    fun addAlbums( album: List<Album>) {
        albums["albumes"] = album
    }

    fun addAlbum(album: Album){
        if(albums.containsKey("albumes")){
            val albumes = albums["albumes"]!!.toMutableList()
            for(i in 0 until albumes.size){
                if(albumes[i].id == album.id){
                    albumes[i] = album
                    albums["albumes"] = albumes
                    break
                }
            }
        }
    }

    fun getAlbums(): List<Album> {
        return if (albums.containsKey("albumes")) albums["albumes"]!! else listOf<Album>()
    }

    fun getAlbum(albumId: Int): Album {
        if(albums.containsKey("albumes")){
            val albumes: List<Album> = albums["albumes"]!!
            for(i in 0 until albumes.size){
                if(albumes[i].id == albumId){
                    return albumes[i]
                }
            }
        }
        return Album(id = 0, name = "", cover = "", releaseDate = "", description = "", genre = "",recordLabel = "", tracks = emptyList(), performers = emptyList())
    }
}