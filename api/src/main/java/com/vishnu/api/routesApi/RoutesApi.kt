package com.vishnu.routesapp.api.routesApi

import retrofit2.Response
import retrofit2.http.GET

interface RoutesApi {

    @GET("https://open-app1.herokuapp.com/data")
    suspend fun fetchRoutesData(): Response<RoutesData>
}