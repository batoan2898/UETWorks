package com.uet.uetworks.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.adapter.StatusAdapter
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.Follows
import com.uet.uetworks.model.Internship
import kotlinx.android.synthetic.main.fragment_status.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StatusFragment : Fragment(), StatusAdapter.OnFollowerClick {

    private lateinit var statusAdapter: StatusAdapter
    lateinit var api: Api
    var dataFollows = MutableLiveData<ArrayList<Follows?>>()
    var dataFollowsResponse: ArrayList<Follows> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerStatusFragment.layoutManager = manager
        statusAdapter = StatusAdapter(context,this)
        recyclerStatusFragment.adapter = statusAdapter
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        dataFollows.observe(this, Observer {
            statusAdapter.setData(it)
        })
        getInternship()
    }

    private fun getInternship() {
        dataFollowsResponse = ArrayList()
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
                        tvStatusTeacher.text = body.lecturers?.fullName
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
                                dataFollowsResponse.studentName,
                                dataFollowsResponse.partnerId
                            )
                        } as ArrayList<Follows?>)
                    }
                }
            })
    }

    private fun selectInternship(id : Int){
        api.selectIntern("/select/intern/$id",MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Internship>{
                override fun onFailure(call: Call<Internship>, t: Throwable) {
                    Log.e("selectMessage",t.message)
                }

                override fun onResponse(call: Call<Internship>, response: Response<Internship>) {
                    if (response.code() == 200){
                        Toast.makeText(requireContext(),"Xác nhận thành công",Toast.LENGTH_SHORT).show()
                        getInternship()
                    }else{
                        Toast.makeText(requireContext(),"Lỗi",Toast.LENGTH_SHORT).show()

                    }
                }
            })

    }

    override fun onItemClick(follows: Follows) {
        Toast.makeText(requireContext(),follows.partnerName,Toast.LENGTH_SHORT).show()
        if (follows.status == "PASS"){
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Xác nhận công ty thực tập")
            builder.setMessage("Bạn chỉ được phép chọn 1 lần, Bạn đã chắc chắn muốn chọn " + follows.partnerName +" là đơn vị thực tập?")
            builder.setPositiveButton(
                "Đồng ý"
            ) { dialog, _ ->
                selectInternship(follows.id)
                dialog.cancel() }

            builder.setNegativeButton(
                "Hủy"
            ) { dialog, _ -> dialog.cancel() }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.uet.uetworks.R.layout.fragment_status, container, false)
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
