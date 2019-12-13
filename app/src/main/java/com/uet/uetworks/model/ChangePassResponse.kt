package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ChangePassResponse (
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("expiryTime") var expiryTime: String,
    @SerializedName("id") var id: Int,
    @SerializedName("lastLogin") var lastLogin: String,
    @SerializedName("lecturers") var lecturers: String,
    @SerializedName("role") var role: String = "STUDENT",
    @SerializedName("status") var status: String,
    @SerializedName("token") var token: String,
    @SerializedName("userName") var userName: String
)