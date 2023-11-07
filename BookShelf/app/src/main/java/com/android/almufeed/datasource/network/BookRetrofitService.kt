package com.android.almufeed.datasource.network

import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import kotlinx.coroutines.flow.Flow

interface BookRetrofitService {
    suspend fun getAllBookList(searchKey: String, apikey: String): Flow<DataState<BookListNetworkResponse>>

}