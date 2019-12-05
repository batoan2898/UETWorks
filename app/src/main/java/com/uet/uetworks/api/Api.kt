package com.uet.uetworks.api

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import com.uet.uetworks.model.EmailVNU
import com.uet.uetworks.model.NewMessage
import com.uet.uetworks.model.Student
import com.uet.uetworks.model.User
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Url



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
    fun getMessage(@Header("auth-token") token: String): Call<List<NewMessage>>



    @SuppressLint("SupportAnnotationUsage")
    @PUT
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @RawRes
    fun seenMessage(
        @Url url: String ,
        @Header("auth-token") token: String
    ): Call<NewMessage>

    @SuppressLint("SupportAnnotationUsage")
    @OPTIONS
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @RawRes
    fun clickMessage(
        @Url url: String ,
        @Header("auth-token") token: String
    ): Call<NewMessage>


    @SuppressLint("SupportAnnotationUsage")
    @GET("/post/postType/Recruitment")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getPost(
        @Query("page")  page: Int,
        @Query("size")  size: Int,
        @Header("auth-token") token: String
    )

    @SuppressLint("SupportAnnotationUsage")
    @GET("/studentInfo")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getStudentInfo(
        @Header("auth-token") token: String
    ): Call<Student>

}


