package com.uet.uetworks.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.model.Content
import kotlinx.android.synthetic.main.item_home_internship_partner.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
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
            var timeStamp = System.currentTimeMillis()/ 1000
            itemView.tvInternshipPartnerName.text = content.title
            itemView.tvInternshipPartnerTime.text = content.datePost
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val time = formatter.parse(content.expiryTime) as Date
            val timeExpiry  = time.time
            Log.e("timestamp",timeStamp.toString())
            if (timeStamp > timeExpiry){
            itemView.tvStatusPost.text = "Hết hạn"
            }
            else{

            }

        }

    }

    interface OnClickPost {
        fun onPostClick(content: Content)
    }
}