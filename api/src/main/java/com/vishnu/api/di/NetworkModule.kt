package com.vishnu.api.di

import com.google.gson.GsonBuilder
import com.vishnu.routesapp.api.routesApi.RoutesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val READ_TIMEOUT = 60
private const val CONNECT_TIMEOUT = 90

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRoutessAPI(): RoutesApi {
        return createAPIClient(createOkHttpClient(), "https://open-app1.herokuapp.com/")
    }

    private fun createOkHttpClient(
        interceptor: Interceptor? = null,
        authenticator: okhttp3.Authenticator? = null
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        // Set interceptor when provided
        interceptor?.let { builder.addInterceptor(it) }
        // Set authenticator when provided
        authenticator?.let { builder.authenticator(it) }
        builder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)

        return builder.build()
    }

    private inline fun <reified T> createAPIClient(httpClient: OkHttpClient, baseUrl: String): T {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create()))
            .build().create(T::class.java)
    }
}
