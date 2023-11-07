package com.android.almufeed.business.data.network.book

import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.datasource.network.BookRetrofitService
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookNetworkDataSourceImpl @Inject constructor(
    private val bookRetrofitService: BookRetrofitService,
    ): BookNetworkDataSource {

    override suspend fun getAllBooks(searchKey: String, apikey: String): Flow<DataState<BookListNetworkResponse>> = flow{
        bookRetrofitService.getAllBookList(searchKey,apikey).collect{ plans ->
            emit(plans)
        }
    }
}