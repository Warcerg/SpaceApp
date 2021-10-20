package com.example.spaceapp.framework.ui.earth

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.model.AppState
import com.example.spaceapp.model.repository.Repository

class EarthViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()


    fun getLiveData() = liveDataToObserve

    fun getEarthPhotoData(date: String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.SuccessEarthPhoto(repository.getEarthPictureFromServer(date)))
        }.start()
    }

}