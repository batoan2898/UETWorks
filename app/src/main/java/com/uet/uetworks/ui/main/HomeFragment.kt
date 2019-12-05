package com.uet.uetworks.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.uet.uetworks.adapter.NewMessageAdapter
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.NewMessage
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment(), NewMessageAdapter.OnClickMessage {


    private var dataAll = MutableLiveData<java.util.ArrayList<NewMessage?>>()
    private lateinit var newMessageAdapter: NewMessageAdapter
    lateinit var api: Api
    private var dataResponse: ArrayList<NewMessage> = arrayListOf()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerNewMessage.layoutManager = manager
        recyclerNewMessage.setHasFixedSize(true)
        newMessageAdapter = NewMessageAdapter(context, this)
        recyclerNewMessage.adapter = newMessageAdapter
        getDataNewMessage()
        Log.e("token",MySharedPreferences.getInstance(requireContext()).getToken())
        Log.e("message",MySharedPreferences.getInstance(requireContext()).getIdMessage())
        initView()
    }



    private fun initView() {
        dataAll.observe(this, Observer {
            newMessageAdapter.setData(it)
        })

        initViewNewMessage()

    }

    private fun initViewNewMessage() {

        btnShowNotification.setOnClickListener {
            if (recyclerNewMessage.isVisible){
                recyclerNewMessage.visibility = View.GONE
            }else if (recyclerNewMessage.isGone){
                recyclerNewMessage.visibility = View.VISIBLE
            }
        }

    }


    private fun getDataNewMessage() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        Log.e("toantoken",MySharedPreferences.getInstance(requireContext()).getToken())
        api.getMessage(MySharedPreferences.getInstance(requireContext()).getToken()).enqueue(object : Callback<List<NewMessage>> {
            override fun onFailure(call: Call<List<NewMessage>>, t: Throwable) {
                Log.e("toan", t.message.toString())

            }

            override fun onResponse(
                call: Call<List<NewMessage>>,
                response: Response<List<NewMessage>>
            ) {
                response.body()?.let { body ->
                    dataResponse.addAll(body)
                    dataAll.postValue(dataResponse.map { dataResponse ->
                        NewMessage(
                            dataResponse.content,
                            dataResponse.id,
                            dataResponse.lastUpdated,
                            dataResponse.messageType,
//                            dataResponse.messages,
                            dataResponse.receiverName,
                            dataResponse.senderName,
                            dataResponse.sendDate,
                            dataResponse.status,
                            dataResponse.title
                        )
                    } as ArrayList<NewMessage?>)

                }
            }

        })
    }


    override fun onMessageClick(message: NewMessage) {
        message.id?.let { MySharedPreferences.getInstance(requireContext()).setIdMessage(it) }
        Log.e("id",MySharedPreferences.getInstance(requireContext()).getIdMessage())
        clickMessage()
        var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(message.title)
        builder.setMessage(message.content)
        builder.setNeutralButton("Ok"){_,_ ->
            seenMessage()
            dataAll.observe(this, Observer {
                it.remove(message)
                newMessageAdapter.notifyDataSetChanged()
            })


        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun seenMessage() {
        Log.e("toan1","message/"+MySharedPreferences.getInstance(requireContext()).getIdMessage()+"/seen")
        Log.e("toan2",MySharedPreferences.getInstance(requireContext()).getToken())
        api.seenMessage("message/"+MySharedPreferences.getInstance(requireContext()).getIdMessage()+"/seen",MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<NewMessage>{
                override fun onFailure(call: Call<NewMessage>, t: Throwable) {
                    Log.e("seen",t.message)
                }

                override fun onResponse(call: Call<NewMessage>, response: Response<NewMessage>) {
                    Log.e("codeSeen", response.code().toString())
                }

            })

    }

    private fun clickMessage() {
        api.clickMessage("message/"+MySharedPreferences.getInstance(requireContext()).getIdMessage()+"/seen",MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<NewMessage>{
                override fun onFailure(call: Call<NewMessage>, t: Throwable) {
                    Log.e("Click",t.message)
                }

                override fun onResponse(call: Call<NewMessage>, response: Response<NewMessage>) {
                    Log.e("codeClickSeen", response.code().toString())
                }

            })

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}

