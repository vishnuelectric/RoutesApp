package com.vishnu.routesapp.ui.routes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vishnu.api.ApiError
import com.vishnu.api.NetworkError
import com.vishnu.api.Success
import com.vishnu.database.data.db.entities.TripData
import com.vishnu.routesapp.ui.base.BaseViewModel

import com.vishnu.usecase.FetchAndSaveRoutesUseCase
import com.vishnu.usecase.ObserveDBUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val POLLING_INTERVAL_MILLIS = 60 * 1000L

/**
 * Routes viewmodel calls various needed usecases to accomplish to task
 * Used usecase to shift business logic from viewmodel to usecase
 */
class RoutesViewModel @ViewModelInject constructor(
    private val andSaveRoutesUseCase: FetchAndSaveRoutesUseCase,
    private val observeDBUseCase: ObserveDBUseCase
) : BaseViewModel() {

    // Observe on this if incase we want to directly show from API call
    private val _routesLiveData: MutableLiveData<List<TripData>> = MutableLiveData();
    val routesLiveData = _routesLiveData

    fun fetchRoutesData() {
        viewModelScope.launch {

            when (val result = andSaveRoutesUseCase()) {
                is Success -> {
                    println(result.value)
                    _routesLiveData.postValue(result.value as List<TripData>)
                }
                is ApiError -> println(result.code)
                is NetworkError -> println(result.ioException.toString())
                is UnknownError -> println(result.toString())
            }
              delay(POLLING_INTERVAL_MILLIS)
        }


    }

    //Observing data from db
    fun observeRoutesDB():LiveData<List<TripData>>{
        return observeDBUseCase()
    }
}

