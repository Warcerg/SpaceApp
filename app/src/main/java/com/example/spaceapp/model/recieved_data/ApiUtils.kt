package com.example.spaceapp.model.recieved_data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object ApiUtils {
    const val baseUrl = "https://api.nasa.gov/"

    const val epicArchiveUrl = "EPIC/archive/natural/"
    const val pngArchiveUrl = "/png/"
    const val pngUrl = ".png?api_key="

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