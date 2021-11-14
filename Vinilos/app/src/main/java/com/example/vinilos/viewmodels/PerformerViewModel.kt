package com.example.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer
import com.example.vinilos.models.PerformerType
import com.example.vinilos.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformerViewModel (application: Application) :  AndroidViewModel(application) {

    private val performerRepository = PerformerRepository(application)
    private val _performers = MutableLiveData<List<Performer>>()
    private val _performerDetail = MutableLiveData<Performer>()

    val performerDetail: LiveData<Performer>
        get() = _performerDetail

    val performers: LiveData<List<Performer>>
        get() = _performers

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    fun showPerformerDetail(performer: Performer){
        _performerDetail.value = performer
    }

    suspend fun refreshDetailPerformerFromNetwork() {
        _performerDetail.value?.let {
            val performerType = PerformerType.valueOf("BAND")
            performerRepository.refreshPerformerDetail(performerType,it.performerId)
        }
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.Default){
                    var data = performerRepository.refreshPerformerList()
                    _performers.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception) {
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}