package com.android.bookshelf.ui.launchpad

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bookshelf.business.domain.utils.dataStore.BasePreferencesManager
import com.android.bookshelf.di.NetworkConnection
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
class LaunchpadViewModel @Inject constructor(
    private val basePreferencesManager: BasePreferencesManager,
) : ViewModel() {

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    fun getPrefUserDetails() = viewModelScope.launch {
        basePreferencesManager.getUserPreference().collect {
            taskEventChannel.send(
                TaskEvent.SetUserDetails(
                    it.userName,
                    it.userMobile,
                    it.userEmail,
                    it.userProfilePic
                )
            )
        }
    }
}

sealed class TaskEvent {
    data class SetUserDetails(
        val name: String,
        val mobileNumber: String,
        val email: String,
        val userProfilePic: String,
    ) : TaskEvent()
}