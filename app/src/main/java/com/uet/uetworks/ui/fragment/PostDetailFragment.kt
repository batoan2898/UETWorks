package com.uet.uetworks.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.*
import com.uet.uetworks.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_post_detail.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class PostDetailFragment : Fragment() {
    lateinit var api: Api
    private var idPost: Int = 0
    private var postTitle: String = ""
    private var studentName: String = ""
    private var timeStamp : Long = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.title_home)
        api = ApiBuilder.client?.create(Api::class.java)!!
        getTimestamp()
        initView()
        getStudentInfo()
        initClickFollow()

    }

    private fun getTimestamp() {
        timeStamp = System.currentTimeMillis() / 1000
    }

    private fun initClickFollow() {
        btnFollowPartner.setOnClickListener {
            if (btnFollowPartner.text == getText(R.string.enrol)) {
                followPost()
            } else {
                unfollowPost()
            }
        }
    }

    private fun getInternship() {
        api.getInternship(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : retrofit2.Callback<Internship> {
                override fun onFailure(call: Call<Internship>, t: Throwable) {
                    Log.e("getInternship", t.message)
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Internship>, response: Response<Internship>) {
                    tvInternshipTitle.text = "Thực tập chuyên nghành đợt " +
                            response.body()?.internshipTerm?.term + " năm " + response.body()?.internshipTerm?.year
                    tvInternshipTime.text =
                        "Thời gian đăng ký thực tập: " + response.body()?.internshipTerm?.startDate +
                                " đến " + response.body()?.internshipTerm?.expiredDate

                    if (response.body()?.createdAt != null) {
                        btnInternship.text = getString(R.string.cancel)
                    }
                }

            })

    }

    private fun getStudentInfo() {
        api.getStudentInfo(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : retrofit2.Callback<Student> {
                override fun onFailure(call: Call<Student>, t: Throwable) {
                    Log.e("getStudentInfo", t.message)
                }

                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    studentName = response.body()?.infoBySchool?.studentName.toString()
                }
            })

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        var bundle: Bundle? = this.arguments
        var content = bundle?.getSerializable("object") as Content
        tvDetailTitle.text = content.title
        tvDetailDatePost.text = "Ngày đăng: " + content.datePost
        tvDetailExpiryTime.text = "Ngày hết hạn: " + content.expiryTime
        tvDetailRequiredNumber.text = "Số lượng cần tuyển: " + content.requiredNumber
        tvDetailPartnerContactName.text = "Tên liên lạc: " + content.partnerContact?.contactName
        tvDetailPartnerContactPhone.text = "Số điện thoại: " + content.partnerContact?.phone
        tvDetailPartnerContactMail.text = "Email: " + content.partnerContact?.email
        idPost = content.idPost
        postTitle = content.title
        webViewPostDetail.settings.javaScriptEnabled = true
        webViewPostDetail.settings.loadWithOverviewMode = true
        webViewPostDetail.settings.useWideViewPort = true
        webViewPostDetail.settings.builtInZoomControls = true
        webViewPostDetail.settings.displayZoomControls = false
        var word = content.contentPost

        webViewPostDetail.loadData(word.replace("color:#", ""), "text/html", "UTF-8")

        val id = bundle.getInt("id")
        Log.e("id", id.toString())
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val time = formatter.parse(content.expiryTime) as Date
        val timeExpiry  = time.date
        if (timeExpiry < timeStamp)
        {
            btnFollowPartner.visibility = View.GONE
            tvStatus.text = "Hết hạn"
        }else{
            if (id != 0) {
                btnFollowPartner.text = getText(R.string.cancel)
                tvStatus.text = "Đang theo dõi"
            }else {
                btnFollowPartner.text = getText(R.string.enrol)
                tvStatus.visibility = View.GONE
            }
        }


    }

    private fun followPost() {
        var postDetail = PostFollow(studentName, postTitle)
        api.followPost(
            "/post/$idPost/follow",
            MySharedPreferences.getInstance(requireContext()).getToken(),
            postDetail
        )
            .enqueue(object : retrofit2.Callback<PostFollow> {
                override fun onFailure(call: Call<PostFollow>, t: Throwable) {
                    Log.e("followPost", t.message)
                }

                override fun onResponse(call: Call<PostFollow>, response: Response<PostFollow>) {
                    if (response.code() == 200) {
                        Toast.makeText(requireContext(), "Đã theo dõi bài đăng", Toast.LENGTH_SHORT)
                            .show()
                        btnFollowPartner.setText(R.string.cancel)
                    }
                }
            })

    }

    private fun unfollowPost() {
        api.unfollow(
            "/post/$idPost/student/unfollow",
            MySharedPreferences.getInstance(requireContext()).getToken(),
            PartnerDTO(null, null, null, null, null, 0)
        )
            .enqueue(object : retrofit2.Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("unfollowPost", t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            requireContext(),
                            "Đã bỏ theo dõi bài đăng",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnFollowPartner.setText(R.string.enrol)
                        getInternship()
                    }
                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }


}
