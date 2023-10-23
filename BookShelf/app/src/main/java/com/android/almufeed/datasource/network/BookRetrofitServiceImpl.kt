package com.android.almufeed.datasource.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.almufeed.business.domain.state.ErrorUtils
import com.android.almufeed.datasource.network.models.bookList.BookData
import com.android.almufeed.datasource.network.paging.BookPagingSource
import com.android.almufeed.datasource.network.retrofit.BookWebServices
import kotlinx.coroutines.flow.Flow

class BookRetrofitServiceImpl constructor(
    private val bookWebServices: BookWebServices,
    private val errorUtils: ErrorUtils
) : BookRetrofitService {

    override suspend fun getAllBookList(q: String, apikey: String): Flow<PagingData<BookData>> =
        Pager(
            config = PagingConfig(
                pageSize = 3,
                initialLoadSize = 5,
                maxSize = 500,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BookPagingSource(bookWebServices, q, apikey, errorUtils) }
        ).flow
}