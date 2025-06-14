package com.example.quizapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "quiz_app_prefs"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_LAST_AUTH_TIME = "last_auth_time"
        private const val AUTH_TIMEOUT = 5 * 60 * 1000L // 5 minutes
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setBiometricEnabled(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_BIOMETRIC_ENABLED, enabled)
            .apply()
    }

    fun isBiometricEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_BIOMETRIC_ENABLED, false)
    }

    fun setFirstLaunch(isFirst: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_FIRST_LAUNCH, isFirst)
            .apply()
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setLastAuthTime(time: Long) {
        sharedPreferences.edit()
            .putLong(KEY_LAST_AUTH_TIME, time)
            .apply()
    }

    fun getLastAuthTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_AUTH_TIME, 0)
    }

    fun isAuthenticationRequired(): Boolean {
        if (!isBiometricEnabled()) return false

        val lastAuthTime = getLastAuthTime()
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastAuthTime) > AUTH_TIMEOUT
    }
}