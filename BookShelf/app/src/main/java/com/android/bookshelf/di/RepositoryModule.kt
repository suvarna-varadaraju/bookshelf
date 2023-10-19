package com.android.bookshelf.di

import com.android.bookshelf.business.data.network.book.BookNetworkDataSource
import com.android.bookshelf.business.repository.BookInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBookInfoRepository(
        bookNetworkDataSource: BookNetworkDataSource
    ): BookInfoRepository{
        return BookInfoRepository(bookNetworkDataSource)
    }
}