package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class GradeLevel (
    @SerializedName("id") var id: Int,
    @SerializedName("shortName") var shortName: String,
    @SerializedName("code") var code: String
)