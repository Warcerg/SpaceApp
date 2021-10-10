package com.example.spaceapp.model.recieved_data.recieved_entity

import com.google.gson.annotations.SerializedName

class MarsPhotoData (
    @field:SerializedName("img_src") val url: String?,
    @field:SerializedName("earth_date") val earthDate: String?,
    @field:SerializedName("id") val photoId: Int?,
    @field:SerializedName("sol") val sol: Int?,
        )
