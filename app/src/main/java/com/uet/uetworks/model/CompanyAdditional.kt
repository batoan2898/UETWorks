package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompanyAdditional(
    @SerializedName("partner") var partner: CompanyId,
    @SerializedName("postTitle") var postTitle: String
) : Serializable