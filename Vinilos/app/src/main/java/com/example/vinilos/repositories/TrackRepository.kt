package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Track
import com.example.vinilos.network.NetworkServiceAdapter

class TrackRepository (private val application: Application){
    suspend fun associateTrack(track: Track, albumId: Int) {
        NetworkServiceAdapter.getInstance(application).postTrack(track, albumId)
    }
}