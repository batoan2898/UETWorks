package com.uet.uetworks.ui.main

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.ChangePassRequest
import com.uet.uetworks.model.ChangePassResponse
import com.uet.uetworks.model.Student
import com.uet.uetworks.ui.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Double.parseDouble
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    lateinit var api: Api
    private lateinit var dataResponse: Student
    private lateinit var dataRequest: Student
    val DATE_PATTERN = "^(3[01]|[12][0-9]|[1-9]|0[1-9])(\\/|-)(0?[1-9]|1[012])(\\/|-)[0-9]{4}\$"
    val PHONENUMBER_PATERN = "[\\\\(]?[+]?(\\d*)[\\\\)]?[-\\s\\.]?(\\d*)[-\\s\\.]?(\\d*)[-\\s\\.]?(\\d*)[-\\s\\.]?(\\d*)"

    var token: String? = null
    var myDialog: Dialog? = null

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
        Log.e("token", MySharedPreferences.getInstance(requireContext()).getToken())
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

        iv_pick_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this.requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    et_dob_input.text =
                        Editable.Factory.getInstance().newEditable("""$dayOfMonth/${monthOfYear + 1}/$year""")
                },
                year,
                month,
                day
            )

            dpd.show()
        }

        btn_update.setOnClickListener {
            profle_frag.hideKeyboard()
            updateInfo()
        }
        profle_frag.setOnClickListener { profle_frag.hideKeyboard() }
        btn_changePass.setOnClickListener {
            myDialog = Dialog(this@ProfileFragment.context!!)
            openResetPassDialog()
        }

    }


    internal fun getInfoUser() {
        api = ApiBuilder.client?.create(Api::class.java)!!
        Log.e("token",MySharedPreferences.getInstance(requireContext()).getToken())
        api.getStudentInfo(MySharedPreferences.getInstance(requireContext()).getToken()).enqueue(object :
            Callback<Student> {
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
                    et_hobby_input.setText(dataResponse.desire)

                    dataRequest = dataResponse;
                }

            }

        })

    }

    internal fun updateInfo(){
        api = ApiBuilder.client?.create(Api::class.java)!!
        var fullName = et_name_input.text
        var address = et_address_input.text
        var birthday = et_dob_input.text
        var email = et_email_input.text
        var phoneNumber = et_phone_input.text
        var skype = et_skype_input.text
        var favor = et_hobby_input.text
        var grade = et_class_input.text
        var major = et_skill_input.text
        var language = et_foreignlg_input.text
        if(validDateandPhone(birthday.toString().trim(), phoneNumber.toString().trim())){
            dataRequest.fullName = fullName.toString()
            dataRequest.address = address.toString()
            dataRequest.birthday=birthday.toString()
            dataRequest.email=email.toString()
            dataRequest.phoneNumber=phoneNumber.toString()
            dataRequest.skype=skype.toString()
            dataRequest.desire=favor.toString()
            dataRequest.infoBySchool.grade=grade.toString()
            dataRequest.infoBySchool.major=major.toString()

            api.updateStudentInfo(MySharedPreferences.getInstance(requireContext()).getToken(), dataRequest)
                .enqueue(object: Callback<Student> {
                    override fun onFailure(call: Call<Student>, t: Throwable) {
                        Log.e("Error student info", t.message.toString())
                        Toast.makeText(this@ProfileFragment.context,"Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Student>, response: Response<Student>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@ProfileFragment.context,"Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        }

                    }

                })
        }

    }

    internal fun openResetPassDialog(){
        val txtclose: TextView
        val btn_close: Button
        val btn_confirm: Button
        val et_oldPass: EditText
        val et_newPass: EditText
        val et_retypeNewPass: EditText
        myDialog?.setContentView(R.layout.dialog_change_password)
        txtclose = myDialog!!.findViewById(R.id.txtclose)
        txtclose.text = "X"
        btn_close= myDialog!!.findViewById(R.id.btn_close) as Button
        btn_confirm = myDialog!!.findViewById(R.id.btn_confirm) as Button
        et_oldPass = myDialog!!.findViewById(R.id.et_old_pass)
        et_newPass = myDialog!!.findViewById(R.id.et_new_pass)
        et_retypeNewPass = myDialog!!.findViewById(R.id.et_retype_pass)
        txtclose.setOnClickListener { myDialog!!.dismiss() }
        btn_close.setOnClickListener { myDialog!!.dismiss() }
        btn_confirm.setOnClickListener {
            profle_frag.hideKeyboard()
            changePass(et_oldPass.text.toString(), et_newPass.text.toString(), et_retypeNewPass.text.toString())

        }
        myDialog!!.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog!!.show()
    }

    internal fun changePass(oldPass: String, newPass: String, retypeNewPass: String){
        api = ApiBuilder.client?.create(Api::class.java)!!

        var changePassRequest = ChangePassRequest(String(Hex.encodeHex(DigestUtils.md5(oldPass))),
            String(Hex.encodeHex(DigestUtils.md5(newPass))),
            retypeNewPass)
//        val json = JSONObject()
//        json.put("oldPassword", String(Hex.encodeHex(DigestUtils.md5(oldPass))))
//        json.put("newPassword", String(Hex.encodeHex(DigestUtils.md5(newPass))))
//        json.put("reTypeNewPass", retypeNewPass)
//        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        api.changePass(MySharedPreferences.getInstance(requireContext()).getToken(), changePassRequest)
            .enqueue(object: Callback<ChangePassResponse>{
                override fun onFailure(call: Call<ChangePassResponse>, t: Throwable) {
                    Log.e("Error change password", t.message.toString())
                    Toast.makeText(this@ProfileFragment.context,"Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ChangePassResponse>,
                    response: Response<ChangePassResponse>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(this@ProfileFragment.context,"Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                        token = response.body()?.token
                        Log.e("newToken",token.toString())
                        MySharedPreferences.getInstance(this@ProfileFragment.context!!).setToken(token = token!!)
                        myDialog!!.dismiss()
                    }else {
                        Toast.makeText(this@ProfileFragment.context,response.message(), Toast.LENGTH_SHORT).show()
                        Log.e("Error: ",response.message() + " - " + response.message())
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

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    fun validateDate(date: String): Boolean {
        return DATE_PATTERN.toRegex().matches(date)
    }
    fun validDateandPhone(date: String, phoneNumber: String): Boolean {
        var res = true
        if (!validateDate(date)) {
            res = false
            Toast.makeText(this@ProfileFragment.context,"Ngày sinh sai định dạng", Toast.LENGTH_SHORT).show();

        } else {
            var birthday = convertString2Date(et_dob_input.text.toString())
            var currentDate = getCurrentDateTime()
            if (birthday == null || (birthday != null && checkAfterDate(birthday, currentDate))) {
                res = false
                Toast.makeText(this@ProfileFragment.context,"Vui lòng nhập ngày nhỏ hơn ngày hiện tại", Toast.LENGTH_SHORT).show();

            }
        }
        if (date.length > 10) {
            res = false
            Toast.makeText(this@ProfileFragment.context,"Nhập sai ngày sinh", Toast.LENGTH_SHORT).show();

        }
        var numeric = true
        try {
            val num = parseDouble(et_phone_input.text.toString())
        } catch (e: NumberFormatException) {
            numeric = false
        }
//        if (!numeric) toast("Chỉ nhập số")

        if (phoneNumber.length<8 || phoneNumber.length >20) {
            res = false
            Toast.makeText(this@ProfileFragment.context,"Số điện thoại phải từ 8 đến 20 số", Toast.LENGTH_SHORT).show();

        }
        if (!validPhoneNumber(phoneNumber)) {
            res = false
            Toast.makeText(this@ProfileFragment.context,"Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();

        }
        return res
    }

    private fun convertString2Date(date: String): Date? {
        val arr = date.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in arr.indices) {
            if (Integer.parseInt(arr[i]) < 10) {
                arr[i] = "0" + arr[i]
            }
        }

        var tmpDate = arr.joinToString("/")
        val parser = SimpleDateFormat("dd/MM/yyyy")
        try {
            return parser.parse(tmpDate)
        } catch (e: ParseException) {
            return null
        }
    }
    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun checkAfterDate(date1: Date, date2: Date): Boolean {
        return date1.compareTo(date2) > 0
    }
    private fun validPhoneNumber(phoneNumber: String): Boolean{
        return PHONENUMBER_PATERN.toRegex().matches(phoneNumber)
    }
    private fun Any.enqueue(callback: Callback<Student>) {
    }
}