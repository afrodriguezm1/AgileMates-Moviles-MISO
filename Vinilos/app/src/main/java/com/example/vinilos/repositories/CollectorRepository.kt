package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Collector
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class CollectorRepository (private val application: Application){

    suspend fun refreshCollectorList(): List<Collector> {
        var resp = emptyList<Collector>()
        try{
            resp = NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(resp)
        }catch (e: Exception) {
            resp = CacheManager.getInstance(application.applicationContext).getCollectors()
        }
        return resp
    }

    suspend fun refreshCollectorDetail(collectorId: Int): Collector {
        return NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
    }
}