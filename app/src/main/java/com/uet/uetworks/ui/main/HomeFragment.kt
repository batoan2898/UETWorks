package com.uet.uetworks.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.uet.uetworks.R
import com.uet.uetworks.adapter.NewMessageAdapter
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.NewMessage
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager


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
        getData()
        initView()
        Log.e("toan", "hwee")
    }

    private fun initView() {
        dataAll.observe(this, Observer {
            newMessageAdapter.setData(it)
        })

    }

    private fun getData() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        api.getMessage().enqueue(object : Callback<List<NewMessage>> {
            override fun onFailure(call: Call<List<NewMessage>>, t: Throwable) {
                Log.e("toan", "failed")
            }

            override fun onResponse(
                call: Call<List<NewMessage>>,
                response: Response<List<NewMessage>>
            ) {
                Log.e("toan", response.code().toString())

                response.body()?.let { body ->
                    dataAll.postValue(dataResponse.map { dataResponse ->
                        NewMessage(
                            dataResponse.content,
                            dataResponse.id,
                            dataResponse.lastUpdated,
                            dataResponse.messageType,
                            dataResponse.messages,
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

