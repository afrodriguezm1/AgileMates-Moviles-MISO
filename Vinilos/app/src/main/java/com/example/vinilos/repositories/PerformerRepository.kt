package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Performer
import com.example.vinilos.models.PerformerType
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class PerformerRepository (private val application: Application){

    suspend fun refreshPerformerList(): List<Performer> {
        try {
            val performers = NetworkServiceAdapter.getInstance(application).getMusicians()
            val bands = NetworkServiceAdapter.getInstance(application).getBands()
            CacheManager.getInstance(application.applicationContext).addPerformers(performers.plus(bands))
            return performers.plus(bands)
        }catch (e: Exception) {
            return CacheManager.getInstance(application.applicationContext).getPerformers()
        }
    }

    suspend fun refreshPerformerDetail(performerType: PerformerType, performerId: Int): Performer {
        try {
            var performer : Performer
            if(performerType == PerformerType.BAND){
                performer = NetworkServiceAdapter.getInstance(application).getBandsDetail(performerId)
            }
            else {
                performer = NetworkServiceAdapter.getInstance(application).getMusicianDetail(performerId)
            }
            CacheManager.getInstance(application.applicationContext).addPerformer(performer)
            return performer
        }catch (e: Exception) {
            return CacheManager.getInstance(application.applicationContext).getPerformer(performerType, performerId)
        }
    }
}