package com.kinected.myapplication.common

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceRepo @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getLastUpdateTime(): Long? {
        return sharedPreferences.getLong("last_update_time", 0)
    }

    fun saveLastUpdateTime(token: Long) {
        sharedPreferences.edit().putLong("last_update_time", token).apply()
    }

}