package com.uet.uetworks.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.Student
import com.uet.uetworks.ui.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    lateinit var api: Api
    private lateinit var dataResponse: Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager = LinearLayoutManager(requireContext())
        getInfoUser()
        Log.e("token",MySharedPreferences.getInstance(requireContext()).getToken())
        setUpView()
    }
    private fun setUpView() {
        btn_logout.setOnClickListener {
            MySharedPreferences.getInstance(requireContext()).setLogin(isLogin = false)
            MySharedPreferences.getInstance(requireContext()).setToken("")
            requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }


    internal fun getInfoUser() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        Log.e("toantoken",MySharedPreferences.getInstance(requireContext()).getToken())
        api.getStudentInfo(MySharedPreferences.getInstance(requireContext()).getToken()).enqueue(object : Callback<Student> {
            override fun onFailure(call: Call<Student>, t: Throwable) {
                Log.e("Error student info", t.message.toString())
            }

            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.code() == 200) {
                    dataResponse = response.body()!!
                    et_name_input.setText(dataResponse.fullName)
                    et_address_input.setText(dataResponse.address)
                    et_dob_input.setText(dataResponse.birthday)
                    et_email_input.setText(dataResponse.email)
                    et_phone_input.setText(dataResponse.phoneNumber)
                    et_skype_input.setText(dataResponse.skype)
                    et_class_input.setText(dataResponse.infoBySchool.studentClass)
                    et_skill_input.setText(dataResponse.infoBySchool.major)
                    et_foreignlg_input.setText("")
                    et_hobby_input.setText("")

                }

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

    private fun Any.enqueue(callback: Callback<Student>) {

    }
