package com.uet.uetworks.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.adapter.NotificationAdapter
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.Notification
import com.uet.uetworks.model.NotificationDetail
import kotlinx.android.synthetic.main.fragment_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NotificationFragment : Fragment(), NotificationAdapter.OnClickNotification {

    private lateinit var notificationAdapter: NotificationAdapter
    private var dataNotification = MutableLiveData<ArrayList<NotificationDetail?>>()
    private var dataNotificationResponse: ArrayList<NotificationDetail> = arrayListOf()
    lateinit var api: Api
    var totalPages: Int = 0
    private var last: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerNotificationFragment.layoutManager = manager
        notificationAdapter = NotificationAdapter(context, this)
        recyclerNotificationFragment.adapter = notificationAdapter
        initView()
    }

    private fun initView() {
        dataNotification.observe(this, Observer {
            notificationAdapter.setData(it)
        })
        if (dataNotificationResponse.isEmpty()) {
            getNotification()
        }
    }

    private fun getNotification() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        api.getNotification(0, 55, MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Notification> {
                override fun onFailure(call: Call<Notification>, t: Throwable) {
                    Log.e("getNotification", t.message)
                }

                override fun onResponse(
                    call: Call<Notification>, response: Response<Notification>
                ) {
                    Log.e("codeNotification", response.code().toString())
                    response.body()?.let { body ->
                        last = body.last
                        totalPages = body.totalPages
                        Log.e("page", last.toString())
                        dataNotificationResponse.addAll(body.listContent)
                        dataNotification.postValue(dataNotificationResponse.map { dataNotificationResponse ->
                            NotificationDetail(
                                dataNotificationResponse.id,
                                dataNotificationResponse.title,
                                dataNotificationResponse.content,
                                dataNotificationResponse.senderName,
                                MySharedPreferences.getInstance(requireContext())
                                    .getDate(dataNotificationResponse.sendDate.toLong()),
                                dataNotificationResponse.messageType,
                                dataNotificationResponse.status,
                                dataNotificationResponse.receiverName,
                                MySharedPreferences.getInstance(requireContext())
                                    .getDate(dataNotificationResponse.lastUpdated.toLong())
                            )
                        } as ArrayList<NotificationDetail?>)
                    }
                }
            })
    }

    override fun onNotificationClick(notificationDetail: NotificationDetail) {
        notificationDetail.id?.let {
            MySharedPreferences.getInstance(requireContext()).setIdMessage(it)
        }
        clickNotification()
        var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(notificationDetail.title)
        builder.setMessage(notificationDetail.content.replace("<br />", "\n"))
        builder.setNeutralButton("OK") { _, _ ->
            seenNotification()
            dataNotification.observe(this, Observer {
                it.remove(notificationDetail)
                notificationAdapter.notifyDataSetChanged()
            })
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun clickNotification() {
        api.clickNotification(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        ).enqueue(object : Callback<NotificationDetail> {
            override fun onFailure(call: Call<NotificationDetail>, t: Throwable) {
                Log.e("click", t.message)
            }

            override fun onResponse(
                call: Call<NotificationDetail>,
                response: Response<NotificationDetail>
            ) {
                Log.e("codeClickSeen", response.code().toString())
            }
        })
    }

    private fun seenNotification() {
        api.seenNotification(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<Notification> {
                override fun onFailure(call: Call<Notification>, t: Throwable) {
                    Log.e("seen", t.message)
                }

                override fun onResponse(
                    call: Call<Notification>,
                    response: Response<Notification>
                ) {
                    Log.e("codeSeen", response.code().toString())
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
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
