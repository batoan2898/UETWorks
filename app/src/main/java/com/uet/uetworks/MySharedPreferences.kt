package com.uet.uetworks

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat
import java.util.*

object MySharedPreferences {

    private const val MY_SHARE_PREFERENCES = "MY_SHARE_PREFERENCES"

    private lateinit var preferences: SharedPreferences

    private const val KEY_LOGIN = "KEY_LOGIN"

    const val TOKEN = ""

    const val ID_MESSAGE = ""


    fun init(context: Context) {
        preferences = context.getSharedPreferences(MY_SHARE_PREFERENCES, Context.MODE_PRIVATE)
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
        return preferences.getBoolean(KEY_LOGIN, true)
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

    fun setIdMessage(id: String){
        preferences.edit{
            it.putString(ID_MESSAGE,id)
        }
    }

    fun getIdMessage(): String{
        return preferences.getString(ID_MESSAGE, ID_MESSAGE).toString()
    }
}