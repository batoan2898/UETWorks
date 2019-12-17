package com.uet.uetworks.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.adapter.StatusAdapter
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.Company
import com.uet.uetworks.model.Follows
import com.uet.uetworks.model.Internship
import kotlinx.android.synthetic.main.fragment_status.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StatusFragment : Fragment() {

    private lateinit var statusAdapter: StatusAdapter
    lateinit var api: Api
    private var dataFollows = MutableLiveData<ArrayList<Follows?>>()
    private var dataFollowsResponse: ArrayList<Follows> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerStatusFragment.layoutManager = manager
        statusAdapter = StatusAdapter(context)
        recyclerStatusFragment.adapter = statusAdapter
        initView()
    }

    private fun initView() {
        dataFollows.observe(this, Observer {
            statusAdapter.setData(it)
        })
        getInternship()
    }

    private fun getInternship() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        api.getInternship(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Internship> {
                override fun onFailure(call: Call<Internship>, t: Throwable) {
                    Log.e("getInternship", t.message)
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Internship>, response: Response<Internship>
                ) {
                    Log.e("codeInternship", response.code().toString())
                    response.body()?.let { body ->
                        tvStatusTeacher.text = body.lecturers.fullName
                        tvStatusKind.text = body.internshipType
                        tvStatusPlace.text = body.partnerName
                        tvStatusTime.text =
                            "Đợt " + body.internshipTerm.term + " năm " + body.internshipTerm.year
                        if (body.score != null) tvStatusMark.text = body.score
                        else tvStatusMark.text = "Chưa có điểm"
                        dataFollowsResponse.addAll(body.follows)
                        dataFollows.postValue(dataFollowsResponse.map { dataFollowsResponse ->
                            Follows(
                                MySharedPreferences.getInstance(requireContext())
                                    .getDate(dataFollowsResponse.createdAt.toLong()),
                                dataFollowsResponse.id,
                                dataFollowsResponse.internshipTerm,
                                dataFollowsResponse.lecturersName,
                                dataFollowsResponse.partnerName,
                                dataFollowsResponse.postId,
                                dataFollowsResponse.postTitle,
                                dataFollowsResponse.status,
                                dataFollowsResponse.student,
                                dataFollowsResponse.studentName
                            )
                        } as ArrayList<Follows?>)
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StatusFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
