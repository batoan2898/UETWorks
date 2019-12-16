package com.uet.uetworks.api

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import com.squareup.okhttp.RequestBody
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.model.*
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
    @GET("/studentInfo")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getStudentInfo(
        @Header("auth-token") token: String
    ): Call<Student>

    @SuppressLint("SupportAnnotationUsage")

    @PUT("/studentInfo")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )

    fun updateStudentInfo(
        @Header("auth-token") token: String,
        @Body student: Student
    ): Call<Student>

    @SuppressLint("SupportAnnotationUsage")
    @PUT
    @RawRes
    fun checkFollowId(
        @Url url: String,
        @Header("auth-token") token: String,
        @Body partnerDTO: PartnerDTO
    ): Call<PartnerDTO>


    @SuppressLint("SupportAnnotationUsage")
    @GET("/changePassword")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun changePass(
        @Header("auth-token") token: String,
        @Body changePassRequest: ChangePassRequest
    ): Call<ChangePassResponse>


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

    @SuppressLint("SupportAnnotationUsage")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @HTTP(method = "DELETE", hasBody = true)
    fun unfollow(
        @Url url: String,
        @Header("auth-token") token: String,
        @Body partnerDTO: PartnerDTO
    ): Call<PartnerDTO>

    @SuppressLint("SupportAnnotationUsage")
    @POST("/addFollowByStudent")
    @Headers("Content-Type: application/json", "Accept: application/json")
    @RawRes
    fun addOtherByStudent(@Body partnerOther: PartnerOther,
                          @Header("auth-token") token: String): Call<Void>
}