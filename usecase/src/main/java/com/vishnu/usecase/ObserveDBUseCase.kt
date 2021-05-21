package com.vishnu.usecase

import androidx.lifecycle.LiveData
import com.vishnu.database.data.db.entities.TripData
import com.vishnu.repo.RoutesDataRepository
import javax.inject.Inject

class ObserveDBUseCase @Inject constructor(private val routesDataRepository: RoutesDataRepository) {
     operator fun invoke(): LiveData<List<TripData>> {
        return routesDataRepository.getDataFromDB()
    }
}