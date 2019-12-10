package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id") var id: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("content") var content: String?,
    @SerializedName("senderName") var senderName: String?,
    @SerializedName("sendDate") var sendDate: String?,
    @SerializedName("messageType") var messageType: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("receiverName") var receiverName: String?,
    @SerializedName("lastUpdated") var lastUpdated: String?
)