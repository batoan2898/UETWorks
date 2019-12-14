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
        var bundle: Bundle? = this.arguments
        var content = bundle?.getSerializable("object") as Content
        tvDetailTitle.text = content.title
        tvDetailDatePost.text = "Ngày đăng: " + content.datePost
        tvDetailExpiryTime.text = "Ngày hết hạn: " + content.expiryTime
        tvDetailRequiredNumber.text = "Số lượng cần tuyển: " + content.requiredNumber
        tvDetailPartnerContactName.text = "Tên liên lạc: " + content.partnerContact?.contactName
        tvDetailPartnerContactPhone.text = "Số điện thoại: " + content.partnerContact?.phone
        tvDetailPartnerContactMail.text = "Email: " + content.partnerContact?.email

        var id = bundle?.getString("id")
        if (id != null){
            btnFollowPartner.setText("Hủy đăng ký")
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
