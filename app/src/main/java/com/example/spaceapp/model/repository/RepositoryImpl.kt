package com.example.spaceapp.model.repository

import com.example.spaceapp.model.entities.POD
import com.example.spaceapp.model.recieved_data.ApiUtils
import com.example.spaceapp.model.recieved_data.POD_data.PictureOfTheDayDataRepo

class RepositoryImpl: Repository {
    override fun getPictureOfTheDayFromServer(): POD {
        val dto = PictureOfTheDayDataRepo.API.getPictureOfTheDay(ApiUtils.apiKey).execute().body()
        return POD(
            copyright = dto?.copyright ?: "",
            date = dto?.date ?: "",
            explanation = dto?.explanation ?: "",
            mediaType = dto?.mediaType ?: "",
            title = dto?.title ?: "",
            url = dto?.url ?: "",
            hdurl = dto?.hdurl ?: ""
        )
    }
}