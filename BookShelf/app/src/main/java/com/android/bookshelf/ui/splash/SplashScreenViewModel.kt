package com.android.bookself.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.android.bookshelf.business.domain.utils.dataStore.BasePreferencesManager
import com.android.bookshelf.di.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    @NetworkConnection private val connectionLiveData: LiveData<Boolean>,
    private val basePreferencesManager: BasePreferencesManager,
) : ViewModel() {
    private val _splashChannel = Channel<SplashEvent>()
    val splashChannelFlow = _splashChannel.receiveAsFlow()

    private val _splashLoading = MutableStateFlow(true)
    val splashLoading = _splashLoading.asStateFlow()
    private val isNetworkConnected = connectionLiveData.asFlow()

    fun initViewModel() {

        viewModelScope.launch(Dispatchers.IO) {

            isNetworkConnected.collectLatest{
                Log.e("REF::::", "init : $it")
                initSplashScreen()
                this.coroutineContext.job.cancel()
            }
        }
    }

    private suspend fun initSplashScreen() {
        Timber.e("TOKEN initSplashScreen")
                if (basePreferencesManager.isUserLogIn().first()) {
                        if (basePreferencesManager.isNewUser().first()) {
                            _splashChannel.send(SplashEvent.NavigateToPersonalInfoActivity)
                        } else {
                            _splashChannel.send(SplashEvent.NavigateToLaunchpadActivity)
                        }
                } else {
                    _splashChannel.send(SplashEvent.NavigateToLoginActivity)
                }
            _splashLoading.value = false
    }


    sealed class SplashEvent {
        object NavigateToLoginActivity : SplashEvent()
        object NavigateToPersonalInfoActivity : SplashEvent()
        object NavigateToLaunchpadActivity : SplashEvent()
    }

}