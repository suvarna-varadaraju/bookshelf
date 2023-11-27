package com.android.almufeed.ui.home

import com.android.almufeed.datasource.network.retrofit.BookWebServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class APIServices {
    companion object {

        var BASE_URL = "https://login.microsoftonline.com/8bd1367c-efa4-40b4-acac-9f3e4c82000b/oauth2/"

        fun create(): BookWebServices{

            val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(BookWebServices::class.java)
        }
    }
}
