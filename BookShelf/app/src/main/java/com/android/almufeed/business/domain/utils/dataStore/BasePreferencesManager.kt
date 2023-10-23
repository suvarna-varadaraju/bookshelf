package com.android.almufeed.business.domain.utils.dataStore

import kotlinx.coroutines.flow.Flow

interface BasePreferencesManager {
    suspend fun updateUserDetails(
        userFirstName: String, userLastName: String,
        email: String,image: String
    )

    suspend fun updateUserMobile(mobile: String)
    suspend fun getUserPreference(): Flow<UserPreferences>
    suspend fun isNewUser(): Flow<Boolean>
    suspend fun isUserLogIn(): Flow<Boolean>
    suspend fun setFirstUser(role: Boolean)
    suspend fun setUserLogin(role: Boolean)

    /*=========================================================*/

    //suspend fun updateUserName(userName: String)


}