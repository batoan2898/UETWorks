package com.uet.uetworks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.uet.uetworks.R
import com.uet.uetworks.model.Content
import kotlinx.android.synthetic.main.fragment_post_detail.*


class PostDetailFragment : Fragment() {
    var bundle: Bundle? = this.arguments
    var content = bundle?.getSerializable("content") as Content

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

    private fun initView() {

        tvDetailTitle.text = content.title
        tvDetailDatePost.text = "Ngày đăng: " + content.datePost
        tvDetailExpiryTime.text = "Ngày hết hạn: " + content.expiryTime
        tvDetailRequiredNumber.text = "Số lượng cần tuyển: " + content.requiredNumber
        tvDetailPartnerContactName.text = "Tên liên lạc: " + content.partnerContact?.phone
        tvDetailPartnerContactPhone.text = "Số điện thoại: " + content.partnerContact?.contactName
        tvDetailPartnerContactMail.text = "Email: " + content.partnerContact?.email


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
