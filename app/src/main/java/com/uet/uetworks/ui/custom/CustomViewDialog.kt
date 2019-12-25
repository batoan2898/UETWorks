package com.uet.uetworks.ui.custom

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.ui.activity.MainActivity
import kotlinx.android.synthetic.main.custom_dialog_layout.*


class CustomViewDialog(var activity: MainActivity, private var adapter: RecyclerView.Adapter<*>) : Dialog(activity){

    internal var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog_layout)

        recyclerView = recyclerViewFollowed
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter

    }






}