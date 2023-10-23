package com.android.almufeed.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.almufeed.business.domain.utils.dataStore.BasePreferencesManager
import com.android.almufeed.di.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @NetworkConnection private val connectionLiveData: LiveData<Boolean>,
    private val basePreferencesManager: BasePreferencesManager
) : ViewModel() {

    private val _loginEventSharedFlow = MutableSharedFlow<LoginEvent>()
    val loginEventSharedFlow = _loginEventSharedFlow.asSharedFlow()
    private val _splashLoading = MutableStateFlow(true)

    init {

        viewModelScope.launch {

            basePreferencesManager.isUserLogIn().onEach {
                delay(3000)
                if (it) {
                    Log.e("SPL::", "isUserLogIn: $it")
                    _loginEventSharedFlow.emit(LoginEvent.NavigateToLaunchpadActivity)
                }
                _splashLoading.value = false
            }.launchIn(this)
        }
    }

    sealed class LoginEvent {
        object NavigateToLaunchpadActivity : LoginEvent()
        object ErrorEvent : LoginEvent()
    }
}