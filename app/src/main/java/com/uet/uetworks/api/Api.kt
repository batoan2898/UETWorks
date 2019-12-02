package com.uet.uetworks.api

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import com.uet.uetworks.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @SuppressLint("SupportAnnotationUsage")
    @POST("/login")
    @Headers("Content-Type: application/json", "Accept: application/json")
    @RawRes
    fun login(@Body login: User): Call<User>
}


