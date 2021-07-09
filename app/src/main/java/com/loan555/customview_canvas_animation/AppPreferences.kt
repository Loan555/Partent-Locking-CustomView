package com.loan555.customview_canvas_animation

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object AppPreferences {
    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences

    private val password = Pair("password", null)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    var myPassword: String?
        get() = preferences.getString(password.first, password.second)
        set(value) = preferences.edit {
            this.putString(password.first, value)
        }
}