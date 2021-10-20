package com.example.spaceapp.framework.ui.mars

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.model.AppState
import com.example.spaceapp.model.repository.Repository

class MarsViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getMarsPhotoData(date: String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.SuccessMarsPhoto(repository.getMarsPictureFromServer(date)))
        }.start()
    }



}