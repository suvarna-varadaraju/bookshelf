package com.android.almufeed.business.repository

import com.android.almufeed.business.data.network.book.BookNetworkDataSource
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookInfoRepository @Inject constructor(
    private val bookNetworkDataSource: BookNetworkDataSource
) {
    suspend fun getAllBooks(searchKey: String,apikey:String): Flow<DataState<BookListNetworkResponse>> = flow {
        bookNetworkDataSource.getAllBooks(searchKey,apikey).collect {
            emit(it)
        }

    }
}