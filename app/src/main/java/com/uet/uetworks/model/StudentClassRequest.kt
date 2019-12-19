package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StudentClassRequest(
    @SerializedName("grade") var grade: String,
    @SerializedName("studentClass") var studentClass: String

): Serializable