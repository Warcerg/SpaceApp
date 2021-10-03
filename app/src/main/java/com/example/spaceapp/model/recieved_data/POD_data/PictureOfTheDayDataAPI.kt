package com.example.spaceapp.model.recieved_data.POD_data

import com.example.spaceapp.model.recieved_data.recieved_entity.EarthPhotoData
import com.example.spaceapp.model.recieved_data.recieved_entity.MarsPhotoListData
import com.example.spaceapp.model.recieved_data.recieved_entity.PODData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PictureOfTheDayDataAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODData>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsSetOfPictures(
        @Query("earth_date") earthDate:String,
        @Query("api_key") apiKey:String
    ): Call<MarsPhotoListData>


    @GET("EPIC/api/natural/date/{date}")
    fun getEarthSetOfPictures(
        @Path("date") date:String,
        @Query("api_key") apiKey:String
    ): Call<List<EarthPhotoData>>

}