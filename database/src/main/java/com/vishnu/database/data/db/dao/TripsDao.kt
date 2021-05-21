package com.vishnu.database.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vishnu.database.data.db.entities.TripData

@Dao
interface TripsDao {
    @Query("SELECT * FROM TripData where departsIn > 0")
    fun getAll(): LiveData<List<TripData>>

    @Insert
    suspend fun insertAll(stocks: List<TripData>)


}
