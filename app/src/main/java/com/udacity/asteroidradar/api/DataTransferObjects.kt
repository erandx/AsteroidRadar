package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroid


/*
*DataTransferObjects responsible for parsing responses from the server or formatting Object to
* send to the Server.
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<AsteroidNetwork>)

@JsonClass(generateAdapter = true)
data class AsteroidNetwork(
        val id: Long,
        val codename: String,
        val closeApproachDate: String,
        val absoluteMagnitude: Double,
        val estimatedDiameter: Double,
        val relativeVelocity: Double,
        val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean


)

// Convert Network Result to Database Objects
fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid>{
    return asteroids.map{
        Asteroid(
                id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
        )

    }
}

fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    return asteroids.map {
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