package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat

data class InternshipTerm (
    @SerializedName("endDate") var endDate: String,
    @SerializedName("expiredDate") var expiredDate: String,
    @SerializedName("id") var id: Int,
    @SerializedName("internshipCount") var internshipCount: Int,
    @SerializedName("notiLecturers") var notiLecturers: Boolean,
    @SerializedName("notiScore") var notiScore: Boolean,
    @SerializedName("postCount") var postCount: Int,
    @SerializedName("startDate") var startDate: String,
    @SerializedName("term") var term: Int,
    @SerializedName("year") var year: Int
) : Serializable
