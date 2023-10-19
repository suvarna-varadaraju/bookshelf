package com.android.bookshelf.business.repository

import androidx.paging.PagingData
import com.android.bookshelf.business.data.network.book.BookNetworkDataSource
import com.android.bookshelf.business.domain.model.bookModel.BookInfo
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