package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GradeLevel (
    @SerializedName("id") var id: Int,
    @SerializedName("shortName") var shortName: String,
    @SerializedName("code") var code: String
) : Serializable