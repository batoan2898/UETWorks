package com.uet.uetworks.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.uet.uetworks.R
import com.uet.uetworks.adapter.NotificationAdapter
import com.uet.uetworks.model.Notification
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : Fragment() {
    private lateinit var adapter: NotificationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val noti = ArrayList<Notification>()
        noti.add(
            Notification(
                "Title 1",
                "Detail 1 Detail 1 Detail 1 Detail 1\n Detail 1 Detail 1 Detail 1 Detail 1 Detail 1 Detail 1 Detail 1 Detail 1"
            )
        )
        noti.add(
            Notification(
                "Title 2",
                "Detail 2 Detail 2 Detail 2 Detail 2\n Detail 2 Detail 2 Detail 2 Detail 2 Detail 2 Detail 2 Detail 2 Detail 2"
            )
        )
        noti.add(
            Notification(
                "Title 3",
                "Detail 3 Detail 3 Detail 3 Detail 3\n Detail 3 Detail 3 Detail 3 Detail 3 Detail 3 Detail 3 Detail 3 Detail 3"
            )
        )
        noti.add(
            Notification(
                "Title 4",
                "Detail 4 Detail 4 Detail 4 Detail 4\n Detail 4 Detail 4 Detail 4 Detail 4 Detail 4 Detail 4 Detail 4 Detail 4"
            )
        )
        noti.add(
            Notification(
                "Title 5",
                "Detail 5 Detail 5 Detail 5 Detail 5\n Detail 5 Detail 5 Detail 5 Detail 5 Detail 5 Detail 5 Detail 5 Detail 5"
            )
        )
        noti.add(
            Notification(
                "Title 6",
                "Detail 6 Detail 6 Detail 6 Detail 6\n Detail 6 Detail 6 Detail 6 Detail 6 Detail 6 Detail 6 Detail 6 Detail 6"
            )
        )
        adapter = NotificationAdapter(noti)
        recyclerNotificationFragment.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
