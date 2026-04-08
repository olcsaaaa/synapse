package com.olidev.synapse_mobile.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStorage by preferencesDataStore(name = "synapse_prefs")

class SessionManager(private val context: Context) {
    private val SESSIONTOKEN = stringPreferencesKey("session_key")
    private val USERID = stringPreferencesKey("user_id")
    val sessionToken: Flow<String?> = context.dataStorage.data.map {
        it[SESSIONTOKEN]
    }

    val userId: Flow<String?> = context.dataStorage.data.map { it[USERID] }

    suspend fun saveSession(token: String, userId: String) {
        context.dataStorage.edit { preferences ->
            preferences[SESSIONTOKEN] = token
            preferences[USERID] = userId
        }
    }

    suspend fun clearSession() {
        context.dataStorage.edit { preferences ->
            preferences.clear()
        }
    }

}