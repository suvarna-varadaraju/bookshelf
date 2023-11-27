package com.android.almufeed.business.domain.utils.dataStore

import kotlinx.coroutines.flow.Flow

interface BasePreferencesManager {
    suspend fun getAccessToken(): Flow<String>
    suspend fun updateAccessToken(accessToken: String)
    suspend fun isUserLogIn(): Flow<Boolean>
    suspend fun setUserLogin(role: Boolean)

}