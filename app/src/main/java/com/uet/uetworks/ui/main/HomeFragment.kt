package com.uet.uetworks.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.uet.uetworks.adapter.PostAdapter
import com.uet.uetworks.model.Content
import com.uet.uetworks.model.Post
import kotlin.collections.ArrayList
import com.uet.uetworks.ui.PostDetailFragment
import kotlinx.android.synthetic.main.fragment_post_detail.*



@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment(), NewMessageAdapter.OnClickMessage, PostAdapter.OnClickPost {

    var page: Int = 0
    var totalPage: Int = 0
    private var lastPage: Boolean = true
    private var dataPost = MutableLiveData<java.util.ArrayList<Content?>>()
    private var dataPostResponse: ArrayList<Content> = arrayListOf()
    private var dataMessage = MutableLiveData<java.util.ArrayList<NewMessage?>>()
    private lateinit var newMessageAdapter: NewMessageAdapter
    private lateinit var postAdapter: PostAdapter
    lateinit var api: Api
    private var dataResponse: ArrayList<NewMessage> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerNewMessage.layoutManager = manager
        recyclerNewMessage.setHasFixedSize(true)
        newMessageAdapter = NewMessageAdapter(context, this)
        recyclerNewMessage.adapter = newMessageAdapter

        val managerPost = LinearLayoutManager(requireContext())
        recyclerPost.layoutManager = managerPost
        recyclerPost.setHasFixedSize(true)
        postAdapter = PostAdapter(context, this)
        recyclerPost.adapter = postAdapter

        getDataNewMessage()
        initView()
    }


    private fun initView() {
        dataMessage.observe(this, Observer {
            newMessageAdapter.setData(it)
        })
        dataPost.observe(this, Observer {
            postAdapter.setData(it)
        })
        initViewNewMessage()
        initViewPost()
    }


    private fun getPost() {
        progressBar.visibility = View.VISIBLE

        api.getPost(0, 100, MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Post> {
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.e("getpost", t.message)
                }

                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    progressBar.visibility = View.GONE

                    Log.e("codePost", response.code().toString())
                    response.body()?.let { body ->
                        lastPage = body.last
                        totalPage = body.totalPage
                        Log.e("page", lastPage.toString())
                        dataPostResponse.addAll(body.listContent)

                        dataPost.postValue(dataPostResponse.map { dataPostResponse ->
                            Content(
                                dataPostResponse.contentPost,
                                MySharedPreferences.getInstance(requireContext()).getDate(
                                    dataPostResponse.datePost.toLong()
                                ),
                                MySharedPreferences.getInstance(requireContext()).getDate(
                                    dataPostResponse.expiryTime.toLong()
                                ),
                                dataPostResponse.listFollows,
                                dataPostResponse.idPost,
                                dataPostResponse.partnerContact,
                                dataPostResponse.partnerName,
                                dataPostResponse.postType,
                                dataPostResponse.requiredNumber,
                                dataPostResponse.status,
                                dataPostResponse.title
                            )
                        } as ArrayList<Content?>)

                    }
                }
            })
    }

    private fun initViewPost() {
        btnShowPost.setOnClickListener {
            if (recyclerPost.isGone) {
                if (dataPostResponse.isEmpty()) {
                    getPost()
                }
                recyclerPost.visibility = View.VISIBLE
            } else {
                recyclerPost.visibility = View.GONE
            }
        }

    }

    override fun onPostClick(content: Content) {
        val postDetailFragment = PostDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("content",content)
        postDetailFragment.arguments = bundle


        activity!!.supportFragmentManager.beginTransaction()
            .replace((view!!.parent as ViewGroup).id, postDetailFragment, "findThisFragment")
            .addToBackStack(null)
            .commit()



    }

    private fun initViewNewMessage() {

        btnShowNotification.setOnClickListener {
            if (recyclerNewMessage.isVisible) {
                recyclerNewMessage.visibility = View.GONE
            } else if (recyclerNewMessage.isGone) {
                recyclerNewMessage.visibility = View.VISIBLE
            }
        }

    }


    private fun getDataNewMessage() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        Log.e("toantoken", MySharedPreferences.getInstance(requireContext()).getToken())
        api.getMessage(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<List<NewMessage>> {
                override fun onFailure(call: Call<List<NewMessage>>, t: Throwable) {
                    Log.e("toan", t.message.toString())

                }

                override fun onResponse(
                    call: Call<List<NewMessage>>,
                    response: Response<List<NewMessage>>
                ) {
                    response.body()?.let { body ->
                        dataResponse.addAll(body)
                        dataMessage.postValue(dataResponse.map { dataResponse ->
                            NewMessage(
                                dataResponse.content,
                                dataResponse.id,
                                dataResponse.lastUpdated,
                                dataResponse.messageType,
//                            dataResponse.messages,
                                dataResponse.receiverName,
                                dataResponse.senderName,
                                MySharedPreferences.getInstance(requireContext()).getDate(
                                    dataResponse.sendDate.toLong()
                                ),
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
        Log.e("id", MySharedPreferences.getInstance(requireContext()).getIdMessage())
        clickMessage()
        var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(message.title)
        builder.setMessage(message.content)
        builder.setNeutralButton("Ok") { _, _ ->
            seenMessage()
            dataMessage.observe(this, Observer {
                it.remove(message)
                newMessageAdapter.notifyDataSetChanged()
            })


        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun seenMessage() {

        api.seenMessage(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<NewMessage> {
                override fun onFailure(call: Call<NewMessage>, t: Throwable) {
                    Log.e("seen", t.message)
                }

                override fun onResponse(call: Call<NewMessage>, response: Response<NewMessage>) {
                    Log.e("codeSeen", response.code().toString())
                }

            })

    }

    private fun clickMessage() {
        api.clickMessage(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<NewMessage> {
                override fun onFailure(call: Call<NewMessage>, t: Throwable) {
                    Log.e("Click", t.message)
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

