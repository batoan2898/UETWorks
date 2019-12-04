package com.uet.uetworks.api

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.model.EmailVNU
import com.uet.uetworks.model.NewMessage
import com.uet.uetworks.model.User
import retrofit2.Call
import retrofit2.http.*
import roboguice.util.SafeAsyncTask.Task


interface Api {
    @SuppressLint("SupportAnnotationUsage")
    @POST("/login")
    @Headers("Content-Type: application/json", "Accept: application/json")
    @RawRes
    fun login(@Body login: User): Call<User>

    @SuppressLint("SupportAnnotationUsage")
    @PUT("/resetPass")
    @Headers("Content-Type: application/json", "Accept: application/json")
    @RawRes
    fun resetPass(@Body email: EmailVNU): Call<EmailVNU>


    @SuppressLint("SupportAnnotationUsage")
    @GET("/message/new")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @RawRes
    fun getMessage(@Header("auth-token") token : String ): Call<List<NewMessage>>

    @SuppressLint("SupportAnnotationUsage")
    @PUT("/message/new")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
        "auth-token: 56ad99ce-ecae-4fa7-8289-c8994918f772"
    )
    @RawRes
    fun clickMessage(): Call<List<NewMessage>>



}


