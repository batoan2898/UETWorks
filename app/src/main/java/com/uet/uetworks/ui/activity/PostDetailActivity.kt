package com.uet.uetworks.ui.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.activity_post_detail.*
import retrofit2.Call
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import com.izettle.html2bitmap.Html2Bitmap
import com.izettle.html2bitmap.content.WebViewContent
import retrofit2.Callback


@Suppress("DEPRECATION")
class PostDetailActivity : AppCompatActivity() {
    lateinit var api: Api
    private var idPost: Int = 0
    private var postTitle: String = ""
    private var studentName: String = ""
    private var timeStamp: Long = 0
    private var timeExpiry: Long? = 0
    private var idCheckFollow: Int? = null
    private var contentPost: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPost = intent.getIntExtra("id", 0)
        setContentView(R.layout.activity_post_detail)
        api = ApiBuilder.client?.create(Api::class.java)!!
        getTimestamp()
        getPostDetail()
        checkFollowPost(idPost)
        getStudentInfo()
        initClickFollow()
    }

    private fun getTimestamp() {
        timeStamp = System.currentTimeMillis()
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
        api.getInternship(MySharedPreferences.getInstance(applicationContext).getToken())
            .enqueue(object : retrofit2.Callback<Internship> {
                override fun onFailure(call: Call<Internship>, t: Throwable) {
                    Log.e("getInternship", t.message)
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Internship>, response: Response<Internship>) {
                    if (response.code() == 200) {
                        tvInternshipTitle.text = "Thực tập chuyên nghành đợt " +
                                response.body()?.internshipTerm?.term + " năm " + response.body()?.internshipTerm?.year
                        tvInternshipTime.text =
                            "Thời gian đăng ký thực tập: " + response.body()?.internshipTerm?.startDate +
                                    " đến " + response.body()?.internshipTerm?.expiredDate

                        if (response.body()?.createdAt != null) {
                            btnInternship.text = getString(R.string.cancel)
                        }
                    }
                }
            })

    }

    private fun getStudentInfo() {
        api.getStudentInfo(MySharedPreferences.getInstance(applicationContext).getToken())
            .enqueue(object : retrofit2.Callback<Student> {
                override fun onFailure(call: Call<Student>, t: Throwable) {
                    Log.e("getStudentInfo", t.message)
                }

                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    studentName = response.body()?.infoBySchool?.studentName.toString()
                }
            })

    }

    private fun initView() {
        Log.e("time", timeExpiry.toString())
        if (timeExpiry!! < timeStamp) {
            btnFollowPartner.visibility = View.GONE
            tvStatus.text = "Hết hạn"
        } else {
            Log.e("postFollow1", idCheckFollow.toString())
            tvStatus.visibility = View.GONE

        }
    }

//        object : AsyncTask<Void, Void, Bitmap>() {
//            override fun doInBackground(vararg voids: Void): Bitmap? {
//                val html =contentPost
//
//                return applicationContext.let {
//                    Html2Bitmap.Builder().setContext(it)
//                        .setContent(WebViewContent.html(html)).build().bitmap
//                }
//            }
//
//            override fun onPostExecute(bitmap: Bitmap?) {
//                if (bitmap != null) {
//                    imageView.setImageBitmap(bitmap)
//                }
//            }
//        }.execute()


    fun getPostDetail() {
        api.getPostDetail(
            "/post/$idPost",
            MySharedPreferences.getInstance(applicationContext).getToken()
        )
            .enqueue(object : Callback<Content> {
                override fun onFailure(call: Call<Content>, t: Throwable) {
                    Log.e("getPostDetail", t.message)
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Content>, response: Response<Content>) {
                    if (response.code() == 200) {
                        tvDetailTitle.text = response.body()?.title
                        tvDetailDatePost.text =
                            "Ngày đăng: " + response.body()?.datePost?.toLong()?.let {
                                MySharedPreferences
                                    .getInstance(applicationContext).getDate(it)
                            }
                        tvDetailExpiryTime.text =
                            "Ngày hết hạn: " + response.body()?.expiryTime?.toLong()?.let {
                                MySharedPreferences
                                    .getInstance(applicationContext).getDate(it)
                            }
                        tvDetailRequiredNumber.text =
                            "Số lượng cần tuyển: " + response.body()?.requiredNumber
                        tvDetailPartnerContactName.text =
                            "Tên liên lạc: " + response.body()?.partnerContact?.contactName
                        tvDetailPartnerContactPhone.text =
                            "Số điện thoại: " + response.body()?.partnerContact?.phone
                        tvDetailPartnerContactMail.text =
                            "Email: " + response.body()?.partnerContact?.email
                        idPost = response.body()?.idPost!!
                        postTitle = response.body()?.title.toString()
                        webViewPostDetail.settings.javaScriptEnabled = true
                        webViewPostDetail.settings.loadWithOverviewMode = true
                        webViewPostDetail.settings.useWideViewPort = true
                        webViewPostDetail.settings.builtInZoomControls = true
                        webViewPostDetail.settings.displayZoomControls = false
                        contentPost = response.body()?.contentPost
                        timeExpiry = response.body()?.expiryTime?.toLong()
                        webViewPostDetail.loadData(
                            contentPost?.replace("color:#", ""),
                            "text/html",
                            "UTF-8"
                        )

                        initView()

                    }
                }
            })
    }

    private fun followPost() {
        var postDetail = PostFollow(studentName, postTitle)
        api.followPost(
            "/post/$idPost/follow",
            MySharedPreferences.getInstance(applicationContext).getToken(),
            postDetail
        )
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("followPost", t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            applicationContext,
                            "Đã theo dõi bài đăng",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnFollowPartner.setText(R.string.cancel)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Không thể theo dõi bài đăng",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            })

    }

    private fun unfollowPost() {
        api.unfollow(
            "/post/$idPost/student/unfollow",
            MySharedPreferences.getInstance(applicationContext).getToken(),
            PartnerDTO(null, null, null, null, null, 0)
        )
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("unfollowPost", t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            applicationContext,
                            "Đã bỏ theo dõi bài đăng",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnFollowPartner.setText(R.string.enrol)
                        getInternship()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Đã có lỗi xảy ra",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }

    private fun checkFollowPost(idPost: Int) {
        var partnerDTO = PartnerDTO(idPost, null, null, null, null, null)
        api.checkFollowId(
            "post/$idPost/checkFollow",
            MySharedPreferences.getInstance(applicationContext).getToken(), partnerDTO
        )
            .enqueue(object : Callback<PartnerDTO> {
                override fun onFailure(call: Call<PartnerDTO>, t: Throwable) {
                    Log.e("checkfollow", t.message)
                }

                override fun onResponse(call: Call<PartnerDTO>, response: Response<PartnerDTO>) {
                    if (response.code() == 200) {
                        idCheckFollow = response.body()?.id
                        Log.e("postFollow", response.body()?.id.toString())
                        if (idCheckFollow != 0) {
                            btnFollowPartner.text = getText(R.string.cancel)
                        } else {
                            btnFollowPartner.text = getText(R.string.enrol)
                        }
                    }
                }
            })
    }


}
