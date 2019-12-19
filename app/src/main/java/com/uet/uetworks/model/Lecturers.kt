package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class Lecturers(
    @SerializedName("about") var about: String,
    @SerializedName("avatar") var avatar: String,
    @SerializedName("email") var email: String,
    @SerializedName("emailVNU") var emailVnu: String,
    @SerializedName("faculty") var facultyName: String,
    @SerializedName("fullName") var fullName: String,
    @SerializedName("phoneNumber") var phoneNumber: String,
    @SerializedName("subject") var subject: String,
    @SerializedName("id") var id: Int = 0
)
