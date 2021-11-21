package com.example.vinilos.cache

import android.content.Context
import android.content.SharedPreferences

object SPrefsCache {
    const val APP_SPREFS = "com.example.vinilos.app"
    const val ALBUMS_SPREFS = "com.example.vinilos..albums"
    fun getPrefs(context: Context, name:String): SharedPreferences {
        return context.getSharedPreferences(name,
            Context.MODE_PRIVATE
        )
    }
}