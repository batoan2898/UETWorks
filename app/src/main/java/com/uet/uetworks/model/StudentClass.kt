package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StudentClass(
    @SerializedName("id") var id: Int,
    @SerializedName("studentClass") var studentClass: String,
    @SerializedName("className") var className: String
    ): Serializable