package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Post(
    @SerializedName("content") var listContent: ArrayList<Content> = arrayListOf(),
    @SerializedName("first") var first: Boolean,
    @SerializedName("last") var last: Boolean,
    @SerializedName("number") var number: Int,
    @SerializedName("numberOfElements") var numberOfElements: Int,
    @SerializedName("size") var size: Int,
    @SerializedName("totalElements") var totalElements: Int,
    @SerializedName("totalPage") var totalPage: Int
)

data class Content(
    @SerializedName("content") var contentPost: String,
    @SerializedName("datePost") var datePost: String,
    @SerializedName("expiryTime") var expiryTime: String,
    @SerializedName("follows") var listFollows: ArrayList<Follows> = arrayListOf(),
    @SerializedName("id") var idPost: Int,
    @SerializedName("partnerContact") var partnerContact: PartnerContact?,
    @SerializedName("partnerName") var partnerName: String,
    @SerializedName("postType") var postType: String,
    @SerializedName("requiredNumber") var requiredNumber: Int,
    @SerializedName("status") var status: String,
    @SerializedName("title") var title: String
) : Serializable




