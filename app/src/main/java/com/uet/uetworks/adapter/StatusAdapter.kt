package com.uet.uetworks.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.Follows
import kotlinx.android.synthetic.main.item_status.view.*

class StatusAdapter(
    context: Context?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data: ArrayList<Follows?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StatusHolder(inflater.inflate(R.layout.item_status, parent, false))
    }

    fun setData(data: java.util.ArrayList<Follows?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val follows = this.data?.get(position)
        if (holder is StatusHolder) {
            showStatus(holder, position)
        }
        if (follows?.status?.equals("SELECTED")!!) {
            holder.itemView.tvCompanyName.setTextColor(Color.parseColor("#1977F3"))
            holder.itemView.tvCompanyStatus.setTextColor(Color.parseColor("#1977F3"))
        }
    }

    private fun showStatus(holder: StatusHolder, position: Int) {
        val item = data?.get(position)
        item?.let { holder.bindData(it) }
    }

    open class StatusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(follows: Follows) {
            itemView.tvCompanyName.text = follows.partnerName
            itemView.tvCompanyStatus.text = follows.status
        }
    }
}