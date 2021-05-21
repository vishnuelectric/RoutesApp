package com.vishnu.repo

import androidx.lifecycle.LiveData
import com.vishnu.database.data.db.entities.TripData
import com.vishnu.api.ApiCallResult
import com.vishnu.routesapp.api.makeApiCall
import com.vishnu.routesapp.api.routesApi.RoutesApi
import com.vishnu.routesapp.api.routesApi.RoutesData
import com.vishnu.database.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutesDataRepository @Inject constructor(private val routesApi: RoutesApi, private val appDB : AppDatabase) {
    suspend fun fetchRoutes(): ApiCallResult<RoutesData> {

        return withContext(Dispatchers.IO) { makeApiCall { routesApi.fetchRoutesData() } }

    }

    suspend fun saveData(tripsData:List<TripData>) {
      appDB.tripDao().insertAll(tripsData)

    }

     fun getDataFromDB():LiveData<List<TripData>>{
        return appDB.tripDao().getAll()
    }
}