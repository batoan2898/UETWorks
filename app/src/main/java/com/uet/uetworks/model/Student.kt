package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Student(
    @SerializedName("address") var address: String,
    @SerializedName("birthday") var birthday: String,
    @SerializedName("desire") var desire: String,
    @SerializedName("email") var email: String,
    @SerializedName("fullName") var fullName: String,
    @SerializedName("id") var id: Int,
    @SerializedName("lecturers") var lecturers: Lecturers,
    @SerializedName("phoneNumber") var phoneNumber: String,
    @SerializedName("skype") var skype: String,
    @SerializedName("studentName") var studentName: String,
    @SerializedName("infoBySchool") var infoBySchool: InfoBySchool
) : Serializable