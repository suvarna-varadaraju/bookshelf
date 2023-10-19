package com.android.bookshelf.datasource.cache

import com.android.bookshelf.datasource.cache.models.book.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookDaoService {

    suspend fun insert(bookEntity: BookEntity): Long
}