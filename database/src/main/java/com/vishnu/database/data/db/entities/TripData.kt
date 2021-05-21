package com.vishnu.database.data.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id","sourceTime"])
data class TripData(
    val id:String,
    val source: String,
    val sourceTime: String,
    val destination: String,
    val destinationTime: String,
    val departsIn: Int,
    val tripDuration: String,
    val totalSeats: Int,
    val availableSeats: Int
)