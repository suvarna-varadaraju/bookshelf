package com.android.almufeed.business.domain.utils.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TAG = "BasePrefManager::"

const val DataStore_NAME = "base_preferences"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)

data class UserPreferences(
    val userName: String,
    val userEmail: String,
    val userMobile: String,
    val userProfilePic: String
)

class BasePreferencesManagerImpl constructor(
    private val context: Context
) : BasePreferencesManager {

    companion object {
        val USER_FIRST_NAME = stringPreferencesKey("user_first.name")
        val USER_LAST_NAME = stringPreferencesKey("user_last.name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_MOBILE = stringPreferencesKey("user_mobile")
        val USER_PROFILE_IMAGE = stringPreferencesKey("user_image")
        val USER_IS_NEW = booleanPreferencesKey("user_is.new")
        val USER_LOGIN_STATUS = booleanPreferencesKey("user_login_status")
    }

    override suspend fun updateUserDetails(
        userFirstName: String,
        userLastName: String,
        email: String,
        image: String
    ) {
        context.datastore.edit { preferences ->
            preferences[USER_FIRST_NAME] = userFirstName
            preferences[USER_LAST_NAME] = userLastName
            preferences[USER_EMAIL] = email
            preferences[USER_PROFILE_IMAGE] = image
        }
    }

    override suspend fun updateUserMobile(mobile: String) {
        context.datastore.edit { preferences ->
            preferences[USER_MOBILE] = mobile
        }
    }

    override suspend fun getUserPreference() = context.datastore.data
        .catch { exeption ->
            if (exeption is IOException) {
                Log.e(TAG, "Error reading preferences: ", exeption)
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }
        .map { preferences ->
            val userFName = preferences[USER_FIRST_NAME] ?: ""
            val userLName = preferences[USER_LAST_NAME] ?: ""
            val userEmail = preferences[USER_EMAIL] ?: ""
            val userMobile = preferences[USER_MOBILE] ?: ""
            val userProfilePic = preferences[USER_PROFILE_IMAGE] ?: ""

            UserPreferences(
                userName = "$userFName $userLName",
                userEmail = userEmail,
                userMobile = userMobile,
                userProfilePic = userProfilePic
            )
        }

    override suspend fun isNewUser() = context.datastore.data
        .catch { exeption ->
            if (exeption is IOException) {
                Log.e(TAG, "Error reading preferences: ", exeption)
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }
        .map { preferences ->
            preferences[USER_IS_NEW] ?: false
        }

    override suspend fun isUserLogIn() = context.datastore.data
        .catch { exeption ->
            if (exeption is IOException) {
                Log.e(TAG, "Error reading preferences: ", exeption)
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }
        .map { preferences ->
            preferences[USER_LOGIN_STATUS] ?: false
        }

    override suspend fun setFirstUser(role: Boolean) {
        context.datastore.edit { preferences ->
            preferences[USER_IS_NEW] = role
        }
    }

    override suspend fun setUserLogin(role: Boolean) {
        context.datastore.edit { preferences ->
            preferences[USER_LOGIN_STATUS] = role
        }
    }
}