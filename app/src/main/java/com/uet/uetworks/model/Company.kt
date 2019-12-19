package com.uet.uetworks.model

import ir.mirrajabi.searchdialog.core.Searchable
import java.io.Serializable

data class Company(val id: String?, val companyName: String?) : Searchable {
    override fun getTitle(): String {
        return companyName.toString()
    }
}