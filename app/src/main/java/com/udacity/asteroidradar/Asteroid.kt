package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabaseAsteroid
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroid_table")
data class Asteroid(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "code_name")
        val codename: String,
        @ColumnInfo(name = "close_approachdate")
        val closeApproachDate: String,
        @ColumnInfo(name = "absolute_magnitude")
        val absoluteMagnitude: Double,
        @ColumnInfo(name = "estimate_diameter")
        val estimatedDiameter: Double,
        @ColumnInfo(name = "relative_velocity")
        val relativeVelocity: Double,
        @ColumnInfo(name = "distance_from_earth")
        val distanceFromEarth: Double,
        @ColumnInfo(name = "is_potentially_hazardous")
        val isPotentiallyHazardous: Boolean) : Parcelable


fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid>{
    return this.map {
        DatabaseAsteroid(
           id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}