package com.uet.uetworks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.Content
import kotlinx.android.synthetic.main.item_home_internship_partner.view.*

class PostAdapter(
    context: Context?,
    private val onClickListener: OnClickPost
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data: ArrayList<Content?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostHolder(inflater.inflate(R.layout.item_home_internship_partner, parent, false))
    }

    fun setData(data: java.util.ArrayList<Content?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = this.data?.get(position)
        if (holder is PostHolder) {
            showPost(holder, position)
        }
        if (onClickListener != null) {
            holder.itemView.setOnClickListener {
                if (content != null) {
                    onClickListener.onPostClick(content)
                }
            }
        }

    }

    private fun showPost(holder: PostHolder, position: Int) {
        val item = data?.get(position)
        item?.let { holder.bindData(it) }
    }

    open class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(content: Content) {
            itemView.tvInternshipPartnerName.text = content.partnerName
            itemView.tvInternshipPartnerTime.text = content.datePost
        }

    }

    interface OnClickPost {
        fun onPostClick(content: Content)
    }
}