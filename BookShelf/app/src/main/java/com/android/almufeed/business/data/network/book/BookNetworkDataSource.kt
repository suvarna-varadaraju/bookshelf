package com.android.almufeed.business.data.network.book

import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import kotlinx.coroutines.flow.Flow

interface BookNetworkDataSource {
    suspend fun getAllBooks(searchKey: String, apikey: String): Flow<DataState<BookListNetworkResponse>>
}