package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EmailVNU(
    @SerializedName("emailVNU") var emailVNU: String) : Serializable