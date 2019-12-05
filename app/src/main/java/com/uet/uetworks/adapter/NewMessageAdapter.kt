package com.uet.uetworks.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.model.NewMessage
import kotlinx.android.synthetic.main.item_notification.view.*
import java.util.*
import kotlin.collections.ArrayList


class NewMessageAdapter (context: Context?, private val onClickListener: OnClickMessage) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewMessageHolder(inflater.inflate(R.layout.item_notification, parent, false))
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data: ArrayList<NewMessage?>? =null


    fun setData(data: java.util.ArrayList<NewMessage?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = this.data?.get(position)
        if (holder is NewMessageHolder) {
            showMessage(holder, position)
        }
        if (onClickListener != null) {
            holder.itemView.setOnClickListener {
                if (message != null) {
                    onClickListener.onMessageClick(message)
                }
            }
        }

    }

    private fun showMessage(holder: NewMessageHolder, position: Int) {
        val item = data?.get(position)
        item?.let { holder.bindData(it) }
    }

    open class NewMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(message: NewMessage) {
            message.sendDate = MySharedPreferences.getDate(message.sendDate!!.toLong())
            itemView.tvMessageTitle.text = message.title
            itemView.tvSendDate.text = message.sendDate.toString()
            itemView.tvSenderName.text = message.senderName
        }

    }



    interface OnClickMessage {
        fun onMessageClick(message: NewMessage)
    }
}