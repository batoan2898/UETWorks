package com.uet.uetworks

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat
import java.util.*


class MySharedPreferences private constructor() {

    companion object {
        private val MY_SHARE_PREFERENCES = "MY_SHARE_PREFERENCES"

        private val sharedPref = MySharedPreferences()

        private lateinit var preferences: SharedPreferences

        private val KEY_LOGIN = "KEY_LOGIN"

        const val TOKEN = "token"

        const val ID_MESSAGE = "idMessage"

        fun getInstance(context: Context): MySharedPreferences {
            if (!::preferences.isInitialized) {
                synchronized(sharedPref::class.java) {
                    if (!::preferences.isInitialized) {
                        preferences =
                            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharedPref
        }

    }


    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    fun checkLogin(): Boolean {
        return preferences.getBoolean(KEY_LOGIN, false)
    }

    fun setLogin(isLogin: Boolean) {
        preferences.edit {
            it.putBoolean(KEY_LOGIN, isLogin)
        }
    }

    fun setToken(token: String) {
        preferences.edit {
            it.putString(TOKEN, token)
        }
    }

    fun getToken(): String {
        return preferences.getString(TOKEN, TOKEN).toString()
    }

    fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString()
    }

    fun setIdMessage(id: String) {
        preferences.edit {
            it.putString(ID_MESSAGE, id)
        }
    }

    fun getIdMessage(): String {
        return preferences.getString(ID_MESSAGE, ID_MESSAGE).toString()
    }
}