package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class Internship(
    @SerializedName("createdAt") var createdAt: Long,
    @SerializedName("endDate") var endDate: Long?,
    @SerializedName("follows") var follows: ArrayList<Follows> = arrayListOf(),
    @SerializedName("id") var id: Int,
    @SerializedName("internshipTerm") var internshipTerm: InternshipTerm,
    @SerializedName("internshipType") var internshipType: String,
    @SerializedName("lecturers") var lecturers: Lecturers,
    @SerializedName("partnerName") var partnerName: String,
    @SerializedName("startDate") var startDate: Long?,
    @SerializedName("student") var student: Student,
    @SerializedName("score") var score: String?
)

