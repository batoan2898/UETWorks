package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Partner(
    @SerializedName("partnerName") var partnerName : String,
    @SerializedName("address") var address: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String
) : Serializable