package com.android.almufeed.datasource.network.retrofit

import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import retrofit2.Response
import retrofit2.http.*

interface BookWebServices {
    @GET("volumes")
    suspend fun getAllBookList(
        @Query("q") q: String,
        @Query("key") count: String,
    ): Response<BookListNetworkResponse>
}