package com.android.almufeed.di

import android.content.Context
import androidx.room.Room
import com.android.almufeed.datasource.cache.BookDaoService
import com.android.almufeed.datasource.cache.BookDaoServiceImpl
import com.android.almufeed.datasource.cache.database.BookDao
import com.android.almufeed.datasource.cache.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            BookDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(bookDatabase: BookDatabase) = bookDatabase.bookDao()

    @Singleton
    @Provides
    fun provideBlogDaoService(agriDao: BookDao): BookDaoService {
        return BookDaoServiceImpl(agriDao)
    }
}

























