package com.android.almufeed.business.repository

import androidx.paging.PagingData
import com.android.almufeed.business.data.network.book.BookNetworkDataSource
import com.android.almufeed.business.domain.model.bookModel.BookInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookInfoRepository @Inject constructor(
    private val bookNetworkDataSource: BookNetworkDataSource
) {
    suspend fun getAllBooks(searchKey: String,apikey:String): Flow<PagingData<BookInfo>> = flow {
        bookNetworkDataSource.getAllBooks(searchKey,apikey).collect {
            emit(it)
        }

    }
}