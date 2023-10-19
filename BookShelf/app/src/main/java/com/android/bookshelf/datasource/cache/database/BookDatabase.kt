package com.android.bookshelf.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.bookshelf.datasource.cache.models.book.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        val DATABASE_NAME: String = "Book_DB"
    }

}