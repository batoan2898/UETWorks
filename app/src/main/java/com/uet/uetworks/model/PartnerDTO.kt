package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class PartnerDTO(
    @SerializedName("postId") var idPost: Int?,
    @SerializedName("id") var id: Int?,
    @SerializedName("partner") var partner: Partner?,
    @SerializedName("status") var status: String?,
    @SerializedName("postTitle") var postTitle: String?,
    @SerializedName("studentId") var studentId: Int?
)

data class Partner(
    @SerializedName("partnerName") var partnerName : String,
    @SerializedName("address") var address: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String
)
