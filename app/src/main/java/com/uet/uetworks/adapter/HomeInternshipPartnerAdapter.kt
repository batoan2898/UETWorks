package com.uet.uetworks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R

class HomeInternshipPartnerAdapter(context: Context?, private val onClickListener: OnClickPost) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val VIEW_TYPE_LOADING = 1
        private val VIEW_TYPE_NORMAL = 0
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> return InternshipPartnerHolder(
                inflater.inflate(
                    R.layout.item_home_internship_partner,
                    parent,
                    false
                )
            )
//            VIEW_TYPE_LOADING -> return LoadingItemHolder(
//                inflater.inflate(
//                    R.layout.item_loading,
//                    parent,
//                    false
//                )
//            )
        }
        return InternshipPartnerHolder(
            inflater.inflate(
                R.layout.item_home_internship_partner,
                parent,
                false
            )
        )

    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    private fun showLoadingView(holder: Any, position: Int) {
    }

    private fun showItemView(holder: InternshipPartnerHolder, position: Int) {
    }

    open class InternshipPartnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    inner class LoadingItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


    interface OnClickPost {

    }
}
