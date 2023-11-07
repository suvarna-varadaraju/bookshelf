package com.android.almufeed.datasource.network

import android.util.Log
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.state.ErrorUtils
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import com.android.almufeed.datasource.network.retrofit.BookWebServices
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookRetrofitServiceImpl constructor(
    private val bookWebServices: BookWebServices,
    private val errorUtils: ErrorUtils
) : BookRetrofitService {

    override suspend fun getAllBookList(q: String, apikey: String): Flow<DataState<BookListNetworkResponse>> =
        flow {
            emit(DataState.Loading(true))
            try {
                val response = bookWebServices.getAllBookList(q,apikey)
                if (response.isSuccessful) {
                    Log.e("AR_SUBSTOP::", "API isSuccessful: ")
                    emit(DataState.Success(response.body()!!))
                    emit(DataState.Loading(false))
                } else {
                    Log.e("AR_SUBSTOP::", "API NOT isSuccessful: ")

                    Log.e("AR_SUBSTOP::", "ERROR : " + response.errorBody()?.string())

                    emit(DataState.Loading(false))
                    emit(DataState.Error(CancellationException("unknown")))
                }
            } catch (e: Exception) {
                Log.e("AR_SUBSTOP::", "Exception: $e.")
                emit(DataState.Loading(false))
                emit(DataState.Error(CancellationException("unknown")))
            }
        }
}