package com.example.spaceapp.model.repository

import android.content.res.Resources
import androidx.core.content.res.TypedArrayUtils.getString
import com.example.spaceapp.R
import com.example.spaceapp.model.entities.EarthPhoto
import com.example.spaceapp.model.entities.MarsPhoto
import com.example.spaceapp.model.entities.Note
import com.example.spaceapp.model.entities.POD
import com.example.spaceapp.model.recieved_data.ApiUtils
import com.example.spaceapp.model.recieved_data.POD_data.PictureOfTheDayDataRepo

class RepositoryImpl: Repository {

    override fun getSpecificPictureOfTheDayFromServer(date: String): POD {
        val dto = PictureOfTheDayDataRepo.API.getSpecificPictureOfTheDay(date, ApiUtils.apiKey).execute().body()
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

    override fun getGeneratedNotes(): MutableList<Pair<Note, Boolean>> {
        //temp hard coded text
        val noteText = "Space is the boundless three-dimensional extent in which objects and events have relative position and direction. In classical physics, physical space is often conceived in three linear dimensions, although modern physicists usually consider it, with time, to be part of a boundless four-dimensional continuum known as spacetime. The concept of space is considered to be of fundamental importance to an understanding of the physical universe. However, disagreement continues between philosophers over whether it is itself an entity, a relationship between entities, or part of a conceptual framework."
        var notes: MutableList<Pair<Note, Boolean>> = ArrayList()
        notes.add(Pair(Note("1st Note", "Some very important text"), false))
        notes.add(Pair(Note("2nd Note", noteText), false))
        notes.add(Pair(Note("3rd Note", noteText ), false))
        notes.add(Pair(Note("4th Note", noteText), false))
        notes.add(Pair(Note("5th Note", noteText), false))
        notes.add(Pair(Note("Test note", noteText), false))
        notes.add(Pair(Note("Long Note title", noteText), false))
        notes.add(Pair(Note("Average Note Title", noteText), false))
        notes.add(Pair(Note("Just one usual note", noteText), false))
        notes.add(Pair(Note("Last generated  Note",noteText), false))
        return notes
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