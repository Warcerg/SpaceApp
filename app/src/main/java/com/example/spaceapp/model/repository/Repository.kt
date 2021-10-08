package com.example.spaceapp.model.repository

import com.example.spaceapp.model.entities.EarthPhoto
import com.example.spaceapp.model.entities.MarsPhoto
import com.example.spaceapp.model.entities.POD

interface Repository {
    fun getPictureOfTheDayFromServer(): POD
    fun getMarsPictureFromServer(date:String): List<MarsPhoto>
    fun getEarthPictureFromServer(date:String): EarthPhoto
}