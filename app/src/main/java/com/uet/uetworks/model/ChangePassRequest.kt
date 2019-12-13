package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class ChangePassRequest (
    @SerializedName("oldPassword") var oldPassword: String,
    @SerializedName("newPassword") var newPassword: String,
    @SerializedName("reTypeNewPass") var reTypeNewPass: String
)