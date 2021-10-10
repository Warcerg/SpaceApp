package com.example.spaceapp.model.repository

import com.example.spaceapp.model.entities.POD

interface Repository {
    fun getPictureOfTheDayFromServer(): POD
}