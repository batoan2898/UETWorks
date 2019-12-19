package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notification(
    @SerializedName("content") var listContent: ArrayList<NotificationDetail> = arrayListOf(),
    @SerializedName("last") var last: Boolean,
    @SerializedName("totalPages") var totalPages: Int,
    @SerializedName("totalElements") var totalElements: Int,
    @SerializedName("first") var first: Boolean,
    @SerializedName("numberOfElements") var numberOfElements: Int,
    @SerializedName("size") var size: Int,
    @SerializedName("number") var number: Int
) :Serializable

data class NotificationDetail(
    @SerializedName("id") var id: String,
    @SerializedName("title") var title: String,
    @SerializedName("content") var content: String,
    @SerializedName("senderName") var senderName: String,
    @SerializedName("sendDate") var sendDate: String,
    @SerializedName("messageType") var messageType: String,
    @SerializedName("status") var status: String,
    @SerializedName("receiverName") var receiverName: String,
    @SerializedName("lastUpdated") var lastUpdated: String
) : Serializable