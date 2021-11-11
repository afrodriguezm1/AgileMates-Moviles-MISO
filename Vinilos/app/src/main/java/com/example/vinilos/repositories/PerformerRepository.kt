package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Performer
import com.example.vinilos.network.NetworkServiceAdapter

class PerformerRepository (private val application: Application){

    suspend fun refreshPerformerList(): List<Performer> {
        val performers = NetworkServiceAdapter.getInstance(application).getMusicians()
        val bands = NetworkServiceAdapter.getInstance(application).getBands()
        return performers.plus(bands)

    }
}