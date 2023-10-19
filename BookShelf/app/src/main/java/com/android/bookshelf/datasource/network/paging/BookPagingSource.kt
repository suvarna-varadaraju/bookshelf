package com.android.bookshelf.datasource.network.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.bookshelf.business.domain.state.ErrorUtils
import com.android.bookshelf.datasource.network.models.bookList.BookData
import com.android.bookshelf.datasource.network.retrofit.BookWebServices
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

private const val TOP_UP_STARTING_PAGE_INDEX = 1

class BookPagingSource(
    private val bookWebServices: BookWebServices,
    private val q: String,
    private val apikey: String,
    private val errorUtils: ErrorUtils
) : PagingSource<Int, BookData>() {

    override fun getRefreshKey(state: PagingState<Int, BookData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookData> {
        val position = params.key ?: TOP_UP_STARTING_PAGE_INDEX

        return try {

            val response = bookWebServices.getAllBookList(q,apikey)
            if (response.isSuccessful) {
                val bookList = response.body()!!.items
                Log.e("TOPUP::", "TopUpPagingSource list size : ${bookList.size}")
                LoadResult.Page(
                    data = bookList,
                    prevKey = if (position == TOP_UP_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (bookList.isEmpty()) null else position + 1
                )
            } else {
                val apiError = errorUtils.parseError(response)!!
                if (apiError.message.isNotEmpty()) {
                    LoadResult.Error(CancellationException(apiError.message))
                } else {
                    LoadResult.Error(CancellationException("Something went wrong"))
                }
            }
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}