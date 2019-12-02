package com.uet.uetworks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.Company

class StatusAdapter(val companyList: ArrayList<Company>) :
    RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_status,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company: Company = companyList[position]
        holder.tvCompanyName.text = company.companyName
        holder.tvCompanyStatus.text = company.companyStatus
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompanyName = itemView.findViewById(R.id.tvCompanyName) as TextView
        val tvCompanyStatus = itemView.findViewById(R.id.tvCompanyStatus) as TextView
    }
}