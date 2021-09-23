package com.example.spaceapp.model.recieved_data.POD_data

import com.example.spaceapp.model.recieved_data.recieved_entity.PODData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayDataAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODData>
}