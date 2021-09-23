package com.example.spaceapp.model.entities

import com.google.gson.annotations.SerializedName

data class POD(
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val mediaType: String?,
    val title: String?,
    val url: String?,
    val hdurl: String?
) {
}