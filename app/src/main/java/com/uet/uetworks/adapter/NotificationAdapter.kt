package com.uet.uetworks.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.NotificationDetail
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter(
    context: Context?,
    private val onClickListener: OnClickNotification
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data: ArrayList<NotificationDetail?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationHolder(inflater.inflate(R.layout.item_notification, parent, false))
    }

    fun setData(data: java.util.ArrayList<NotificationDetail?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notificationDetail = this.data?.get(position)
        if (holder is NotificationHolder) {
            showNotification(holder, position)
        }
        if (notificationDetail?.status?.equals("NEW")!!) {
            Log.e("color",notificationDetail?.toString())
//            holder.itemView.setBackgroundColor(Color.parseColor())
//            holder.itemView.tvMessageTitle.setTextColor(Color.parseColor("#1977F3"))
//            holder.itemView.tvSendDate.setTextColor(Color.parseColor("#1977F3"))
//            holder.itemView.tvSenderName.setTextColor(Color.parseColor("#1977F3"))
        }
        holder.itemView.setOnClickListener {
            onClickListener.onNotificationClick(notificationDetail)
        }
    }

    private fun showNotification(holder: NotificationHolder, position: Int) {
        val item = data?.get(position)
        item?.let { holder.bindData(it) }
    }

    open class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(notificationDetail: NotificationDetail) {
            itemView.tvMessageTitle.text = notificationDetail.title
            itemView.tvSendDate.text = notificationDetail.sendDate
            itemView.tvSenderName.text = notificationDetail.senderName
        }
    }

    interface OnClickNotification {
        fun onNotificationClick(notificationDetail: NotificationDetail)
    }
}