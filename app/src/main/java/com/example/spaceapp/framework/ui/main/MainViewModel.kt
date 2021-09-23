package com.example.spaceapp.framework.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.model.AppState
import com.example.spaceapp.model.repository.Repository

class MainViewModel(private val repository: Repository): ViewModel(), LifecycleObserver {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

}