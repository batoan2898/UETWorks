package com.uet.uetworks.api

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.ResponseBody
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
    ): Call<Void>

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
    @GET("/grade-levels")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getGradeLevels(
        @Header("auth-token") token: String
    ): Call<ArrayList<GradeLevel>>

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
        ): Call<Void>

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
	@GET("/studentClass")
    @RawRes
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getStudentClass(
        @Header("auth-token") token: String
    ): Call<ArrayList<StudentClass>>

    @SuppressLint("SupportAnnotationUsage")
    @PUT("/infoBySchool/class")
    @RawRes
    fun putStudentClass(
        @Header("auth-token") token: String,
        @Body studentClassRequest: StudentClassRequest
    ):Call<Void>

	@SuppressLint("SupportAnnotationUsage")
    @PUT("/changePassword")    
    @RawRes
    fun changePass(
        @Header("auth-token") token: String,
        @Body changePassRequest: ChangePassRequest
    ): Call<ChangePassResponse>

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
    @POST("/internship")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun postInternship(
        @Header("auth-token") token: String
    ): Call<Internship>

    @SuppressLint("SupportAnnotationUsage")
    @DELETE("/internship")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun deleteInternship(
        @Header("auth-token") token: String
    ): Call<Void>

    @SuppressLint("SupportAnnotationUsage")
    @GET("/partnerId/name/fit")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getFit(
        @Header("auth-token") token: String
    ): Call<ArrayList<ArrayList<Number>>>

    @SuppressLint("SupportAnnotationUsage")
    @GET("/partnerId/name/other")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getOther(
        @Header("auth-token") token: String
    ): Call<ArrayList<ArrayList<Number>>>

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
    ): Call<Void>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @PUT
    fun followPost(
        @Url url: String,
        @Header("auth-token") token: String,
        @Body postFollow: PostFollow
    ): Call<Void>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST
    fun selectIntern(
        @Url url: String,
        @Header("auth-token") token: String
    ): Call<Internship>



    @SuppressLint("SupportAnnotationUsage")
    @POST("/addFollowByStudent")
    @Headers("Content-Type: application/json", "Accept: application/json")
    @RawRes
    fun addOtherByStudent(@Body partnerOther: PartnerOther,
                          @Header("auth-token") token: String): Call<Void>


    @SuppressLint("SupportAnnotationUsage")
    @POST("/addFollowByStudent")
    @Headers("Content-Type: application/json", "Accept: application/json")
    @RawRes
    fun addCompany(@Body company: CompanyAdditional,
                          @Header("auth-token") token: String): Call<Void>
}