package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


data class NewMessage(
    @SerializedName("content") var content: String,
    @SerializedName("id") var id: String?,
    @SerializedName("lastUpdated") var lastUpdated: String,
    @SerializedName("messageType") var messageType: String,
//    @SerializedName("messages") var messages: Arrays?,
    @SerializedName("receiverName") var receiverName: String,
    @SerializedName("senderName") var senderName: String,
    @SerializedName("sendDate") var sendDate: String,
    @SerializedName("status") var status: String,
    @SerializedName("title") var title: String?
)