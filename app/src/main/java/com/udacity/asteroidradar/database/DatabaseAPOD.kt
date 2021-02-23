package com.udacity.asteroidradar.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.PictureOfDay

@Entity(tableName = "table_picture_of_the_day")
data class DatabasePictureOfDay constructor(
        @PrimaryKey
        val mediaType: String,
        val title: String,
        val url: String
)

//Extension fun to convert from Database objects to Domain Objects
fun DatabasePictureOfDay.asDomainModel(): PictureOfDay {
    return PictureOfDay(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
    )
}