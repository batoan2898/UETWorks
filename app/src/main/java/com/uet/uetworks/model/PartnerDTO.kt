package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class PartnerDTO(
    @SerializedName("idPost") var idPost: Int,
    @SerializedName("id") var id: String?,
    @SerializedName("partner") var partner: Partner?,
    @SerializedName("status") var status: String?
)

data class Partner(
    @SerializedName("address") var address: String,
    @SerializedName("partnerName") var partnerName : String,
    @SerializedName("phone") var phone: String
)
