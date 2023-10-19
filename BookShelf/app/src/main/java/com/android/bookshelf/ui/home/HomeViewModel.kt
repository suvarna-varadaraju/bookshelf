package com.android.bookshelf.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.bookshelf.business.domain.model.bookModel.BookInfo
import com.android.bookshelf.business.domain.utils.exhaustive
import com.android.bookshelf.business.repository.BookInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookInfoRepository: BookInfoRepository,
) : ViewModel() {
    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    private val _topupDataStateApp: MutableLiveData<PagingData<BookInfo>> = MutableLiveData()
    val topupDataStateApp: LiveData<PagingData<BookInfo>>
        get() = _topupDataStateApp

    private fun setStateEvent(state: MarketState) {
        viewModelScope.launch(Dispatchers.IO) {
            when (state) {
                is MarketState.GetMyList -> {
                    Log.e("MARKET::" , "setStateEvent")
                    bookInfoRepository.getAllBooks(
                        state.searchValue,
                        "AIzaSyAjcruS4knXbzq_3HkQtd2k5DZhP5gVWVc"
                    ).cachedIn(viewModelScope).onEach {
                        _topupDataStateApp.value = it
                    }.launchIn(viewModelScope)
                }
                }
            }.exhaustive
        }

    fun getMyList(searchValue: String) {
        setStateEvent(MarketState.GetMyList(searchValue))
    }

    fun setMyList(list: List<BookInfo>) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.SetMyList(list))
    }

}

sealed class MarketState {
    data class GetMyList(val searchValue: String) : MarketState()
}

sealed class TaskEvent {
    data class SetMyList(val list: List<BookInfo>): TaskEvent()
}