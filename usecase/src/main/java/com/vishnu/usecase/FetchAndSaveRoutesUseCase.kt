package com.vishnu.usecase

import com.vishnu.core.TimeUtils
import com.vishnu.api.ApiCallResult

import com.vishnu.api.Success
import com.vishnu.api.UnknownError
import com.vishnu.database.data.db.entities.TripData
import com.vishnu.routesapp.api.routesApi.RoutesData
import com.vishnu.repo.RoutesDataRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Fetch and save routes use case
 */
class FetchAndSaveRoutesUseCase @Inject constructor(private val routesDataRepository: RoutesDataRepository) {
    suspend operator fun invoke(): ApiCallResult<*> {
        return try {

            when (val result = routesDataRepository.fetchRoutes()) {
                is Success -> processValue(result.value)
                else -> result
            }
        } catch (e: Exception) {
            e.printStackTrace()
            UnknownError<List<TripData>>(e)
        }

    }

    private suspend fun processValue(value: RoutesData): Success<List<TripData>> {
        val tripList = mutableListOf<TripData>()
        value.routeInfo.forEach { routeInfo ->
            value.routeTimings[routeInfo.id]?.forEach {
                    tripList.add(
                        TripData(
                            id = routeInfo.id,
                            source = routeInfo.source,
                            sourceTime = TimeUtils.formatDate(TimeUtils.convertToDate(it.tripStartTime)),
                            destination = routeInfo.destination,
                            destinationTime = TimeUtils.formatDate(TimeUtils.addTime(
                                routeInfo.tripDuration,
                                it.tripStartTime
                            )),
                            departsIn = TimeUtils.getDepartsIn(it.tripStartTime),
                            tripDuration = routeInfo.tripDuration,
                            availableSeats = it.avaiable,
                            totalSeats = it.totalSeats
                        )
                    )

            }


        }
        routesDataRepository.saveData(tripList)
        return Success(tripList)
    }
}