package com.android.bookshelf.datasource.cache.models.book

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "book_id")
    var book_id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "author")
    var author: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "publishdate")
    var publishdate: String,

    @ColumnInfo(name = "publisher")
    var publisher: String,

    @ColumnInfo(name = "rating")
    var rating: String,

    @ColumnInfo(name = "image")
    var image: String
)