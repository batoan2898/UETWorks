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

        const val TOKEN = "token"

        const val ID_MESSAGE = "idMessage"

        const val USER_NAME = "user"

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
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }

    fun setIdMessage(id: String) {
        preferences.edit {
            it.putString(ID_MESSAGE, id)
        }
    }

    fun getIdMessage(): String {
        return preferences.getString(ID_MESSAGE, ID_MESSAGE).toString()
    }

    fun setUser(userName: String){
        preferences.edit{
            it.putString(USER_NAME,userName)
        }
    }

    fun getUser(): String{
        return preferences.getString(USER_NAME, USER_NAME).toString()
    }
}