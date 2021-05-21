package com.vishnu.routesapp.api

import com.google.gson.Gson
import com.vishnu.api.*
import retrofit2.Response
import java.io.IOException

 suspend fun <T> makeApiCall(
    apiCall: suspend () -> Response<T>,

): ApiCallResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                @Suppress("UNCHECKED_CAST")
                (Success(null as T))
            } else {
                Success(body)
            }
        } else {
            val rawResponse = response.errorBody()?.string()
            ApiError(
                code = response.code(),
                rawResponse = rawResponse,
                errorResponse = rawResponse?.let { Gson().fromJson(rawResponse, ErrorResponse::class.java) }
            )
        }
    } catch (e: IOException) {
        NetworkError(e)
    }
}


