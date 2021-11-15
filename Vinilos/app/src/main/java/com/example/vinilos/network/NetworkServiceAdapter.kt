package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer
import com.example.vinilos.models.PerformerType
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://back-vinyls-populated.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(id = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                throw it
            }))
    }

    suspend fun getMusicians() = suspendCoroutine<List<Performer>>{ cont->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Performer>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Performer(
                            id = PerformerType.MUSICIAN.toString() + "_" + item.getInt("id"),
                            performerId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            date = item.getString("birthDate"),
                            performerType = PerformerType.MUSICIAN,
                            albums = listOf<Album>()
                        )
                    )
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                throw it
            }))
    }

    suspend fun getBands() = suspendCoroutine<List<Performer>>{ cont->
        requestQueue.add(getRequest("bands",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Performer>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Performer(
                            id = PerformerType.BAND.toString() +"_" + item.getInt("id"),
                            performerId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            date = item.getString("creationDate"),
                            performerType = PerformerType.BAND,
                            albums = listOf<Album>()
                        )
                    )
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                throw it
            }))
    }


    suspend fun postAlbum(album: Album){
        val url = BASE_URL + "albums"

        val albumJSONObject = JSONObject()
        albumJSONObject.put("name",album.name)
        albumJSONObject.put("cover",album.cover)
        albumJSONObject.put("releaseDate",album.releaseDate)
        albumJSONObject.put("description",album.description)
        albumJSONObject.put("genre",album.genre)
        albumJSONObject.put("recordLabel",album.recordLabel)

        requestQueue.add(object : JsonObjectRequest(Method.POST, url, albumJSONObject,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    Log.i("StartActivity", response.toString())
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    Log.i("StartActivity", error.toString())
                }
            }) {

            override fun getHeaders(): Map<String, String> {
                Log.d("parametros post",body.toString())
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                return params
            }
        })
    }

    suspend fun getBandsDetail(performerId: Int) = suspendCoroutine<Performer>{ cont->
        val url = "bands/$performerId"
        requestQueue.add(getRequest(url,
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val albumList = mutableListOf<Album>()
                for (i in 0 until resp.getJSONArray("albums").length()) {
                    val item = resp.getJSONArray("albums").getJSONObject(i)
                    albumList.add(i, Album(id = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                val performer = Performer(id = "BAND_" + resp.getInt("id"),
                    performerId = resp.getInt("id"),
                    performerType = PerformerType.BAND,
                    name = resp.getString("name"),
                    image = resp.getString("image"),
                    description = resp.getString("description"),
                    date = resp.getString("creationDate"),
                    albums = albumList
                )

                cont.resume(performer)
            },
            Response.ErrorListener {
                throw it
            }))
    }

    suspend fun getMusicianDetail(performerId: Int) = suspendCoroutine<Performer>{ cont->
        val url = "musicians/$performerId"
        requestQueue.add(getRequest(url,
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val albumList = mutableListOf<Album>()
                for (i in 0 until resp.getJSONArray("albums").length()) {
                    val item = resp.getJSONArray("albums").getJSONObject(i)
                    albumList.add(i, Album(id = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                val performer = Performer(id = "BAND_" + resp.getInt("id"),
                    performerId = resp.getInt("id"),
                    performerType = PerformerType.MUSICIAN,
                    name = resp.getString("name"),
                    image = resp.getString("image"),
                    description = resp.getString("description"),
                    date = resp.getString("birthDate"),
                    albums = albumList
                )
                cont.resume(performer)
            },
            Response.ErrorListener {
                throw it
            }))
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }
}