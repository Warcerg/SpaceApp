package com.example.spaceapp.model.entities

data class POD(
    val copyright: String,
    val date: String,
    val explanation: String = "test",
    val mediaType: String,
    val title: String,
    val url: String,
    val hdurl: String
) {
}