package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostFollow(
    @SerializedName("studentName") var studentName: String,
    @SerializedName("postTitle") var postTitle: String
) : Serializable