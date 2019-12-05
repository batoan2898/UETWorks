package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable


data class User(
    @SerializedName("userName") var userName: String,
    @SerializedName("password") var password: String,
    @SerializedName("token") var token: String?,
    @SerializedName("studentId") var studentId: Int?,
    @SerializedName("role") var role: String?,
    @SerializedName("infoBySchoolId") var infoBySchool: Int?,
    @SerializedName("id") var id: Int?
) : Serializable
