package com.android.almufeed.ui.login.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.almufeed.business.domain.utils.dataStore.BasePreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    private val basePreferencesManager: BasePreferencesManager,
) : ViewModel() {
    var valid: Boolean = false
    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    fun updatePersonalInfo(mobileNumber : String) = viewModelScope.launch {
        basePreferencesManager.updateUserMobile(mobileNumber)
        basePreferencesManager.setFirstUser(true)
        basePreferencesManager.setUserLogin(true)
        taskEventChannel.send(TaskEvent.NavigateToLaunchpad)
        /*if (basePreferencesManager.isNewUser().first()) {
            taskEventChannel.send(TaskEvent.NavigateToPersonalInfo)
        } else {
            taskEventChannel.send(TaskEvent.NavigateToLaunchpad)
        }*/
    }
}

sealed class TaskEvent {
    object NavigateToPersonalInfo : TaskEvent()
    object NavigateToLaunchpad : TaskEvent()
}