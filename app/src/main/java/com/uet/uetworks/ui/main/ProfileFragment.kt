package com.uet.uetworks.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uet.uetworks.R
import com.uet.uetworks.Service.ProfilesService
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.StudentInfo
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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


    internal fun getInfoUser(studentId: String) {
//        val retrofit = ApiBuilder.client;
//
//        val requestApi = retrofit?.create(Api::class.java!!)
//
//        val call = requestApi?.getInfoStudent(userId)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://112.137.129.69:8180")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProfilesService::class.java)
        val call = service.getInfoStudent(studentId)
     
        call.enqueue(object : Callback<StudentInfo> {
                override fun onResponse(call: Call<StudentInfo>, response: Response<StudentInfo>) {
                    if(response.code() == 200){
                        val studentInfo = response.body()!!
                        et_name_input.setText(studentInfo.fullName)
                        et_dob_input.setText(studentInfo.birthday)

                        et_email_input.setText(studentInfo.email)
                        et_address_input.setText(studentInfo.address)
                        et_phone_input.setText(studentInfo.phoneNumber)
                    }
                }

                override fun onFailure(call: Call<StudentInfo>, t: Throwable) {
                    AlertDialog.Builder(activity!!)
                        .setTitle("Waring")
                        .setMessage("Can't get user data")
                        .setCancelable(true)
                        .show()
                }
        })
        
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    
}

private fun Any.enqueue(callback: Callback<StudentInfo>) {

}
