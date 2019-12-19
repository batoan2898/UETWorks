package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PartnerDTO(
    @SerializedName("postId") var idPost: Int?,
    @SerializedName("id") var id: Int?,
    @SerializedName("partner") var partner: Partner?,
    @SerializedName("status") var status: String?,
    @SerializedName("postTitle") var postTitle: String?,
    @SerializedName("studentId") var studentId: Int?
): Serializable


