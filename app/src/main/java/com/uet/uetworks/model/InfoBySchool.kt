package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class InfoBySchool(
    @SerializedName("diploma") var diploma: String?,
    @SerializedName("emailvnu") var emailvnu: String?,
    @SerializedName("gpa") var gpa: Double?,
    @SerializedName("grade") var grade: String?,
    @SerializedName("graduationYear") var graduationYear: String?,
    @SerializedName("id") var id: Int?,
    @SerializedName("major") var major: String?,
    @SerializedName("studentClass") var studentClass: String?,
    @SerializedName("studentCode") var studentCode: Int?,
    @SerializedName("studentName") var studentName: String?
)

