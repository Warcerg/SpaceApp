package com.example.spaceapp.model

import com.example.spaceapp.model.entities.EarthPhoto
import com.example.spaceapp.model.entities.MarsPhoto
import com.example.spaceapp.model.entities.POD

sealed class AppState {
    data class SuccessPOD(val pictureOfTheDayData: POD) : AppState()
    data class SuccessMarsPhoto(val marsPhotos: List<MarsPhoto>): AppState()
    data class SuccessEarthPhoto(val earthPhoto: EarthPhoto): AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}