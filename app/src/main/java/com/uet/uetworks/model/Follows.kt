package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class Follows(
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("id") var id: Int,
    @SerializedName("internshipTerm") var internshipTerm: Int,
    @SerializedName("lecturersName") var lecturersName: String?,
    @SerializedName("partnerName") var partnerName: String,
    @SerializedName("postId") var postId: Int,
    @SerializedName("postTitle") var postTitle: String,
    @SerializedName("status") var status: String,
    @SerializedName("student") var student: Student,
    @SerializedName("studentName") var studentName: String
)