package com.android.bookshelf.business.data.network.book

import androidx.paging.PagingData
import com.android.bookshelf.business.domain.model.bookModel.BookInfo
import kotlinx.coroutines.flow.Flow

interface BookNetworkDataSource {
    suspend fun getAllBooks(searchKey: String, apikey: String): Flow<PagingData<BookInfo>>
}