package com.android.almufeed.datasource.network

import androidx.paging.PagingData
import com.android.almufeed.datasource.network.models.bookList.BookData
import kotlinx.coroutines.flow.Flow

interface BookRetrofitService {
    suspend fun getAllBookList(searchKey: String, apikey: String): Flow<PagingData<BookData>>

}