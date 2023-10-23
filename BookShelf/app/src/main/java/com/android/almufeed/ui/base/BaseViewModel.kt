package com.android.almufeed.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.almufeed.di.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    @NetworkConnection private val connectionLiveData: LiveData<Boolean>
) : ViewModel() {

    val isNetworkConnected = connectionLiveData
}