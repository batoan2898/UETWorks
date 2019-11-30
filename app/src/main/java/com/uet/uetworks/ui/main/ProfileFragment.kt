package com.uet.uetworks.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.StudentInfo
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }


//    fun getInfoUser(userId: String) {
//        val retrofit = ApiBuilder.getApi()
//
//        val requestApi = retrofit.create(Api::class.java!!)
//
//        val call = requestApi.getInfoUser(userId)
//
//        call.enqueue(object : Callback<StudentInfo>() {
//            override fun onResponse(call: Call<StudentInfo>, response: Response<StudentInfo>) {
//                val userData = response.body().getInfo()
//                et_name_input.text=userData.getName()
//                et_dob_input.text=(userData.getBdate())
//
//                et_email_input.text=(userData.getEmail())
//                et_address_input.text=(userData.getAddr())
//                et_phone_input.text=(userData.getPhone())
//            }
//
//            override fun onFailure(call: Call<StudentInfo>, t: Throwable) {
//                AlertDialog.Builder(getBaseContext())
//                    .setTitle("Waring")
//                    .setMessage("Can't get user data")
//                    .setCancelable(true)
//                    .show()
//            }
//        })
//    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

//    fun getBaseContext(): Context?{
//
////        Context context =
//    }
}
