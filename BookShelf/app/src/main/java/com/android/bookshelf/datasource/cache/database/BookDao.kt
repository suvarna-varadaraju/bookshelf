package com.android.bookshelf.datasource.cache.database

import androidx.room.*
import com.android.bookshelf.datasource.cache.models.book.BookEntity

@Dao
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity): Long

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * from Books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * from Books WHERE book_id= :bookId")
    fun getBookById(bookId: String): BookEntity
}