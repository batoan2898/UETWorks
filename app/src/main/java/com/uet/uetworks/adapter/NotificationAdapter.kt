package com.uet.uetworks.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.Notification
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter(context: Context?, private val onClickListener: onClickNotification) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_notification,
                parent,
                false
            )
        )
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data: ArrayList<Notification?>? = null

    fun setData(data: ArrayList<Notification?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = this.data?.get(position)
        if (holder is ViewHolder) {
            showNotification(holder, position)
        }
        if (onClickListener != null) {
            holder.itemView.setOnClickListener {
                if (notification != null) {
                    onClickListener.onNotificationClick(notification)
                }
            }
        }
    }

    private fun showNotification(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        item?.let { holder.bindData(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(notification: Notification) {
            itemView.tvSenderName.text = notification.senderName
            itemView.tvSendDate.text = notification.sendDate
            itemView.tvMessageTitle.text = notification.title
        }
    }

    interface onClickNotification {
        fun onNotificationClick(notification: Notification)
    }
}