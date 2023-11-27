package com.android.almufeed.ui.home.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.dataStore.BasePreferencesManager
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.business.repository.BookInfoRepository
import com.android.almufeed.datasource.network.models.events.GetEventListResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventsViewModel @Inject constructor(
    private val bookInfoRepository: BookInfoRepository,
    private val basePreferencesManager: BasePreferencesManager
) : ViewModel() {
    private val _myEventDataSTate: MutableLiveData<DataState<GetEventListResponseModel>> = MutableLiveData()
    val myEventDataSTate: LiveData<DataState<GetEventListResponseModel>> get() = _myEventDataSTate

    fun requestForEvent() = viewModelScope.launch {
        setStateEvent()
    }

    private fun setStateEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            bookInfoRepository.getEventList(
                basePreferencesManager.getAccessToken().first()
            ).onEach {
                _myEventDataSTate.value = it
            }.launchIn(viewModelScope)
        }.exhaustive
    }
}