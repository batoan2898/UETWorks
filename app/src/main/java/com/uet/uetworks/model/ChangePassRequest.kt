package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChangePassRequest (
    @SerializedName("oldPassword") var oldPassword: String,
    @SerializedName("newPassword") var newPassword: String,
    @SerializedName("reTypeNewPass") var reTypeNewPass: String
) :Serializable