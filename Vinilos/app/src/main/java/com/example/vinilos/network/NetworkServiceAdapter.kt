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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import com.android.volley.AuthFailureError
import com.example.vinilos.models.Performer
import com.example.vinilos.models.Track
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://public-back-sandbox-vinyls.herokuapp.com/"
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

                    val performers = item.getJSONArray("performers")
                    val performerList = mutableListOf<Performer>()
                    for (j in 0 until item.getJSONArray("performers").length()){
                        performerList.add(Performer(performerId = performers.getJSONObject(j).getInt("id"), name = performers.getJSONObject(j).getString("name"), image = performers.getJSONObject(j).getString("image"), description = performers.getJSONObject(j).getString("description")))
                    }

                    list.add(i, Album(id = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), performers = performerList, tracks = mutableListOf<Track>()))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                throw it
            }))
    }

    suspend fun getAlbum(idAlbum: Int) = suspendCoroutine<Album> { cont ->
        requestQueue.add(getRequest("albums/" + idAlbum.toString(),
            Response.Listener<String> { response ->
                val resp = JSONObject(response)

                val performers = resp.getJSONArray("performers")
                val performerList = mutableListOf<Performer>()
                for (j in 0 until resp.getJSONArray("performers").length()){
                    performerList.add(Performer(performerId = performers.getJSONObject(j).getInt("id"), name = performers.getJSONObject(j).getString("name"), image = performers.getJSONObject(j).getString("image"), description = performers.getJSONObject(j).getString("description")))
                }

                val tracks = resp.getJSONArray("tracks")
                val tracksList = mutableListOf<Track>()
                for (j in 0 until resp.getJSONArray("tracks").length()){
                    tracksList.add(Track(id = tracks.getJSONObject(j).getInt("id"), name = tracks.getJSONObject(j).getString("name"), duration = tracks.getJSONObject(j).getString("duration")))
                }

                cont.resume(Album(id = resp.getInt("id"),name = resp.getString("name"), cover = resp.getString("cover"), recordLabel = resp.getString("recordLabel"), releaseDate = resp.getString("releaseDate"), genre = resp.getString("genre"), description = resp.getString("description"), performers = performerList, tracks = tracksList))
            },
            Response.ErrorListener {
                throw it
            }))

    }


    fun postAlbum(body: JSONObject){
        val url = BASE_URL + "albums"

        requestQueue.add(object : JsonObjectRequest(Method.POST, url, body,
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