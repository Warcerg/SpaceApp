package com.example.spaceapp.model

import com.example.spaceapp.model.entities.POD
import com.example.spaceapp.model.recieved_data.recieved_entity.PODData

sealed class AppState {
    data class Success(val pictureOfTheDayData: POD) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}