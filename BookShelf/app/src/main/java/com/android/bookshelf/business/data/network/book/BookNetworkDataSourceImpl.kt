package com.android.bookshelf.business.data.network.book

import androidx.paging.PagingData
import androidx.paging.map
import com.android.bookshelf.business.domain.model.bookModel.BookInfo
import com.android.bookshelf.datasource.network.BookRetrofitService
import com.android.bookshelf.datasource.network.mappers.BookListMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookNetworkDataSourceImpl @Inject constructor(
    private val bookRetrofitService: BookRetrofitService,
    private val bookListMapper: BookListMapper
    ): BookNetworkDataSource {

    override suspend fun getAllBooks(searchKey: String, apikey: String): Flow<PagingData<BookInfo>> = flow{
        bookRetrofitService.getAllBookList(searchKey,apikey).collect{ plans ->
            emit(plans.map { bookListMapper.mapFromEntity(it) })
        }
    }
}