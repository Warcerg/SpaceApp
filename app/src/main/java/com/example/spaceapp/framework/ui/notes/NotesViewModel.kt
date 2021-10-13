package com.example.spaceapp.framework.ui.notes

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.model.AppState
import com.example.spaceapp.model.repository.Repository

class NotesViewModel(private val repository: Repository): ViewModel(), LifecycleObserver {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getNotesData() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.SuccessNotes(repository.getGeneratedNotes()))
        }.start()

    }
}