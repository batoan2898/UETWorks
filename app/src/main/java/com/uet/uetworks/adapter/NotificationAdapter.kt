package com.uet.uetworks.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.Notification

class NotificationAdapter(val notificationList: ArrayList<Notification>) :
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

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification: Notification = notificationList[position]
        holder.tvNotificationTitle.text = notification.notificationTitle
        holder.tvNotificationDetail.text = notification.notificationDetail
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNotificationTitle = itemView.findViewById(R.id.tvNotificationTitle) as TextView
        val tvNotificationDetail = itemView.findViewById(R.id.tvNotificationDetail) as TextView
    }
}