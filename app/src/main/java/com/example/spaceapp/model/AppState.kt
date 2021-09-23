package com.example.spaceapp.model

import com.example.spaceapp.model.recieved_data.recieved_entity.PODData

sealed class AppState {
    data class Success(val serverResponseData: PODData) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}