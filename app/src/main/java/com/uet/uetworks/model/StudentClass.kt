package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class StudentClass(
    @SerializedName("id") var id: Int,
    @SerializedName("studentClass") var studentClass: String,
    @SerializedName("className") var className: String
    )