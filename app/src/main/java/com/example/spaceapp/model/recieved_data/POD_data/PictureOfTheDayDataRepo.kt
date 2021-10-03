package com.example.spaceapp.model.recieved_data.POD_data

import com.example.spaceapp.model.recieved_data.ApiUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PictureOfTheDayDataRepo {
    val API: PictureOfTheDayDataAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()

        adapter.create(PictureOfTheDayDataAPI::class.java)
    }
}