package com.android.almufeed.business.data.network.book

import androidx.paging.PagingData
import com.android.almufeed.business.domain.model.bookModel.BookInfo
import kotlinx.coroutines.flow.Flow

interface BookNetworkDataSource {
    suspend fun getAllBooks(searchKey: String, apikey: String): Flow<PagingData<BookInfo>>
}