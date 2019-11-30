package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable


data class User(@SerializedName("userName") var userName:String,
                @SerializedName("password") var password: String) : Serializable
