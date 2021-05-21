package com.vishnu.routesapp.api.routesApi

import com.vishnu.api.routesApi.RouteInfo

data class RoutesData(
    val routeInfo: List<RouteInfo>,
    val routeTimings: Map<String,List<RouteTiming>>
)