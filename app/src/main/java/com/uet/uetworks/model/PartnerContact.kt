package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PartnerContact(
    @SerializedName("about") var about: String,
    @SerializedName("address") var address: String,
    @SerializedName("avatar") var avatar: String,
    @SerializedName("contactName") var contactName: String,
    @SerializedName("email") var email: String,
    @SerializedName("id") var id: Int,
    @SerializedName("phone") var phone: String,
    @SerializedName("skype") var skype: String
) : Serializable