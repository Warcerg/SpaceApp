package com.example.spaceapp.model.recieved_data

import okhttp3.Interceptor
import spaceapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit


object ApiUtils {
    val baseUrl = "https://api.nasa.gov/"

   const val apiKey = "MhOwqoXjH6miEF4JCCa57ahHbT9zrPtusRvtVmPk"

    fun getOkHTTPBuilder():OkHttpClient {
        val httpClient =OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }

        return httpClient.build()
    }


}