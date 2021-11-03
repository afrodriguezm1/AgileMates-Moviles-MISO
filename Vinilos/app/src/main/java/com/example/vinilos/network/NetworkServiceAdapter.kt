package com.example.vinilos.network

import android.content.Context
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.vinilos.brokers.VolleyBroker
import com.example.vinilos.models.Album
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        lateinit var volleyBroker: VolleyBroker
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
                volleyBroker = VolleyBroker(context.applicationContext)
            }
    }
    fun getAlbums(
        onComplete: (resp: List<Album>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ){
        volleyBroker.instance.add(VolleyBroker.getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Album(
                            id = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            recordLabel = item.getString("recordLabel"),
                            releaseDate = item.getString("releaseDate"),
                            genre = item.getString("genre"),
                            description = item.getString("description")
                        )
                    )
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }
    fun postAlbum(
        body: JSONObject,
        albumId: Int,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ){
        volleyBroker.instance.add(VolleyBroker.postRequest("albums", body,
            { response ->
                onComplete(response)
            },
            {
                onError(it)
            }
        ))
    }
}