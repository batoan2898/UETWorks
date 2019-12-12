package com.uet.uetworks.model

import com.google.gson.annotations.SerializedName

data class PartnerFitOther(
    @SerializedName("") var listPartner: ArrayList<Fit> = arrayListOf()
)

data class Fit(
    @SerializedName("0") var id : Int,
    @SerializedName("1") var partnerName: String
)