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
import kotlinx.android.synthetic.main.fragment_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NotificationFragment : Fragment(), NotificationAdapter.onClickNotification {
    private var dataAll = MutableLiveData<java.util.ArrayList<Notification?>>()
    private var dataResponse: ArrayList<Notification> = arrayListOf()
    private lateinit var notificationAdapter: NotificationAdapter
    lateinit var api: Api

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerNotificationFragment.layoutManager = manager
        recyclerNotificationFragment.setHasFixedSize(false)
        notificationAdapter = NotificationAdapter(context, this)
        recyclerNotificationFragment.adapter = notificationAdapter
        getDataNotification()
        Log.e("token", MySharedPreferences.getInstance(requireContext()).getToken())
        Log.e("message", MySharedPreferences.getInstance(requireContext()).getIdMessage())
        initView()
    }

    private fun initView() {
        dataAll.observe(this, Observer {
            notificationAdapter.setData(it)
        })
        recyclerNotificationFragment.visibility = View.VISIBLE
    }

    private fun getDataNotification() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        Log.e("toantoken", MySharedPreferences.getInstance(requireContext()).getToken())
        api.getNotification(0, 100, MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<List<Notification>> {
                override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                    Log.e("toan", t.message.toString())
                }

                override fun onResponse(
                    call: Call<List<Notification>>,
                    response: Response<List<Notification>>
                ) {
                    response.body()?.let { body ->
                        dataResponse.addAll(body)
                        dataAll.postValue(dataResponse.map { dataResponse ->
                            Notification(
                                dataResponse.id,
                                dataResponse.title,
                                dataResponse.content,
                                dataResponse.senderName,
                                dataResponse.sendDate,
                                dataResponse.messageType,
                                dataResponse.status,
                                dataResponse.receiverName,
                                dataResponse.lastUpdated
                            )
                        } as ArrayList<Notification?>)
                    }
                }
            })
    }

    override fun onNotificationClick(notification: Notification) {
        notification.id?.let {
            MySharedPreferences.getInstance(requireContext()).setIdMessage(it)
        }
        Log.e("id", MySharedPreferences.getInstance(requireContext()).getIdMessage())
        clickNotification()
        var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(notification.title)
        builder.setMessage(notification.content)
        builder.setNeutralButton("OK") { _, _ ->
            seenNotification()
            dataAll.observe(this, Observer {
                it.remove(notification)
                notificationAdapter.notifyDataSetChanged()
            })
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun seenNotification() {
        Log.e(
            "toan1",
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen"
        )
        Log.e("toan2", MySharedPreferences.getInstance(requireContext()).getToken())
        api.seenNotification(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<Notification> {
                override fun onFailure(
                    call: Call<Notification>,
                    t: Throwable
                ) {
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

    private fun clickNotification() {
        api.clickNotification(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<Notification> {
                override fun onFailure(
                    call: Call<Notification>,
                    t: Throwable
                ) {
                    Log.e("Click", t.message)
                }

                override fun onResponse(
                    call: Call<Notification>,
                    response: Response<Notification>
                ) {
                    Log.e("codeClickSeen", response.code().toString())
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
