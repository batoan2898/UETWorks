package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompanyId(
    @SerializedName("id") var id : Int
) : Serializable
