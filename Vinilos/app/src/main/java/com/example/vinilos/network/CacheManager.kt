package com.example.vinilos.network

import android.content.Context
import androidx.collection.ArrayMap
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer
import com.example.vinilos.models.PerformerType
import androidx.collection.arrayMapOf
import com.example.vinilos.models.Collector

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

    private val collectors : ArrayMap<String, List<Collector>> = arrayMapOf<String, List<Collector>>()

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

    fun addPerformers(performer: List<Performer>){
        performers["performers"] = performer
    }

    fun addPerformer(performer: Performer){
        if(performers.containsKey("performers")){
            val perfor = performers["performers"]!!.toMutableList()
            for(i in 0 until perfor.size){
                if(perfor[i].id == performer.id){
                    perfor[i] = performer
                    performers["performers"] = perfor
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

    fun getPerformers(): List<Performer>{
        return if (performers.containsKey("performers")) performers["performers"]!! else listOf<Performer>()
    }

    fun getPerformer(performerType: PerformerType, performerId: Int): Performer{
        if(performers.containsKey("performers")){
            val performers: List<Performer> = performers["performers"]!!
            for(i in 0 until performers.size){
                if(performers[i].performerId == performerId && performers[i].performerType == performerType){
                    return performers[i]
                }
            }
        }
        return Performer(
            id = "", performerId = 0 , name = "", image = "", description = "", date = "", performerType = PerformerType.MUSICIAN, albums = listOf<Album>()
        )
    }

    fun addCollectors( collector: List<Collector>) {
        collectors["collectors"] = collector
    }

    fun getCollectors(): List<Collector> {
        return if (collectors.containsKey("collectors")) collectors["collectors"]!! else listOf<Collector>()
    }
}