package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class PartnerOther(
    @SerializedName("partner") var partner: Objects?,
    @SerializedName("partnerDTO") var partnerDTO: Partner,
    @SerializedName("postTitle") var postTitle: String
): Serializable

