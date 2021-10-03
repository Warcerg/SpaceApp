package com.example.spaceapp.model.repository

import com.example.spaceapp.model.entities.EarthPhoto
import com.example.spaceapp.model.entities.MarsPhoto
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

    override fun getMarsPictureFromServer(date: String): List<MarsPhoto> {
        val dto = PictureOfTheDayDataRepo.API.getMarsSetOfPictures(date, ApiUtils.apiKey).execute().body()
        val marsPhotoList = mutableListOf<MarsPhoto>()
        if (dto?.photos != null){
            for (photos in dto.photos){
                marsPhotoList.add(
                    MarsPhoto(
                        url = photos.url ?: "",
                        earthDate = photos.earthDate ?: "",
                        photoId = photos.photoId ?: 0,
                        sol = photos.sol ?: 0
                    )
                )
            }
        }
        return  marsPhotoList
    }

    override fun getEarthPictureFromServer(date: String): EarthPhoto {
        val dto = PictureOfTheDayDataRepo.API.getEarthSetOfPictures(date, ApiUtils.apiKey).execute().body()
        val image = dto?.get(0)?.image ?: ""
            return EarthPhoto(
                caption = dto?.get(0)?.caption ?: "",
                photoId = dto?.get(0)?.photoId ?: "",
                photoDate = dto?.get(0)?.photoDate ?: "",
                imageURL = getEarthImageURl(date, image)
            )
    }

    private fun getEarthImageURl(date: String, image: String): String {
        val formattedDate = date.replace("-", "/")
        return ApiUtils.baseUrl
            .plus(ApiUtils.epicArchiveUrl)
            .plus(formattedDate)
            .plus(ApiUtils.pngArchiveUrl)
            .plus(image)
            .plus(ApiUtils.pngUrl)
            .plus(ApiUtils.apiKey)
    }
}