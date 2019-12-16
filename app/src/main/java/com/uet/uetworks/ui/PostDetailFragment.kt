package com.uet.uetworks.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uet.uetworks.R
import com.uet.uetworks.model.Content
import kotlinx.android.synthetic.main.fragment_post_detail.*




class PostDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

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
        webViewPostDetail.settings.javaScriptEnabled = true
        webViewPostDetail.settings.loadWithOverviewMode = true
        webViewPostDetail.settings.useWideViewPort = true
        webViewPostDetail.settings.builtInZoomControls = true
        webViewPostDetail.settings.displayZoomControls = false
        var word = content.contentPost
        var guess = ";color:#"
        var index = word.indexOf(guess)
        var sub : String
        var arr :ArrayList<String>  = arrayListOf()
        while (index >= 0) {
            sub = word.substring(index,index +14)
            Log.e("index",sub)
            index = word.indexOf(guess, index + 1)
        }

        webViewPostDetail.loadData(word.replace("color:#",""),"text/html","UTF-8")



        val id = bundle.getInt("id")
        Log.e("id",id.toString())
        if (id != 0){
            btnFollowPartner.text = "Hủy đăng ký"
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PostDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
