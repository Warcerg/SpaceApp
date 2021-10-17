package com.example.spaceapp.model.recieved_data.recieved_entity

import com.google.gson.annotations.SerializedName

class EarthPhotoData (
    @field:SerializedName("image") val image: String?,
    @field:SerializedName("caption") val caption: String?,
    @field:SerializedName("identifier") val photoId: String?,
    @field:SerializedName("date") val photoDate: String?,
        )
