package com.uet.uetworks.api

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.model.*
import retrofit2.Call
import retrofit2.http.*
import roboguice.util.SafeAsyncTask.Task
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
        "Accept: application/json"
    )
    @RawRes
    fun seenMessage(
        @Url url: String,
        @Header("auth-token") token: String
    ): Call<NewMessage>

    @SuppressLint("SupportAnnotationUsage")
    @OPTIONS
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @RawRes
    fun clickMessage(
        @Url url: String,
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
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("auth-token") token: String
    ): Call<Post>

    @SuppressLint("SupportAnnotationUsage")
    @GET("/message")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getNotification(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("auth-token") token: String
    ): Call<Notification>

    @SuppressLint("SupportAnnotationUsage")
    @OPTIONS
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @RawRes
    fun clickNotification(
        @Url url: String,
        @Header("auth-token") token: String
    ): Call<NotificationDetail>

    @SuppressLint("SupportAnnotationUsage")
    @PUT
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @RawRes
    fun seenNotification(
        @Url url: String,
        @Header("auth-token") token: String
    ): Call<Notification>

    @SuppressLint("SupportAnnotationUsage")
    @GET
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @RawRes
    fun getPostDetail(
        @Url url: String,
        @Header("auth-token") token: String
    ): Call<Content>


    @SuppressLint("SupportAnnotationUsage")
    @PUT
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun checkFollowId(
        @Url url: String,
        @Header("auth-token") token: String,
        @Body partnerDTO: PartnerDTO
    ): Call<PartnerDTO>


    @SuppressLint("SupportAnnotationUsage")
    @GET("/internship")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getInternship(
        @Header("auth-token") token: String
    ): Call<Internship>


    @SuppressLint("SupportAnnotationUsage")
    @PUT("/internship")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun putInternship(
        @Header("auth-token") token: String
    ): Call<Internship>


    @SuppressLint("SupportAnnotationUsage")
    @GET("/partnerId/name/fit")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getFit(
        @Header("auth-token") token: String
    ): Call<PartnerFitOther>

    @SuppressLint("SupportAnnotationUsage")
    @GET("/partnerId/name/other")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getOther(
        @Header("auth-token") token: String
    ): Call<PartnerFitOther>
}


