package com.vishnu.api

import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.lang.Exception

sealed class ApiCallResult<T>

data class Success<T>(
    val value: T
) : ApiCallResult<T>()

data class NetworkError<T>(
    val ioException: IOException
) : ApiCallResult<T>()

data class UnknownError<T>(
    val exception: Exception
) : ApiCallResult<T>()

data class ApiError<T>(
    val code: Int,
    val errorResponse: ErrorResponse? = null,
    val rawResponse: String? = null
) : ApiCallResult<T>() {
    override fun toString(): String {
        return "   code=$code \n" +
            "   errorCode=${errorResponse?.code}, \n" +
            "   status=${errorResponse?.status} \n" +
            "   rawResponse=$rawResponse \n"
    }
}

class ErrorResponse(
    val code: String?,
    val status: String?,
    @SerializedName("Message")
    val message: String? = null
) {
    fun getTextError() = status ?: message
}
