package com.uet.uetworks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uet.uetworks.CommonMethod
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.User
import kotlinx.android.synthetic.main.activity_login.*
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.view.ViewGroup
import android.view.View
import android.widget.EditText




class LoginActivity : AppCompatActivity(), Callback<User> {

    lateinit var api: Api
    var userName: String = ""
    var password: String = ""
    var token: String? = null
    var id: Int? = null
    var infoBySchoolId: Int? = null
    var studentId: Int? = null
    var role: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupUI(findViewById(R.id.parent))
        edtUser.setText(MySharedPreferences.getInstance(this).getUser())
        initKeyboard()

        api = ApiBuilder.client?.create(Api::class.java)!!
        btnLogin.setOnClickListener {
            if (checkLogin()) {
                if (CommonMethod.isNetworkAvailable(this))
                    loginRetrofit(userName, password)
                else CommonMethod.showToast( this)
            }
        }
        tvForgotPass.setOnClickListener {
            val intent = Intent(this, RecoverPassActivity::class.java)
            startActivity(intent)
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager!!.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard(this@LoginActivity)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount) {
                val innerView = (view as ViewGroup).getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun initKeyboard() {
        edtPass.setOnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                if (checkLogin()) {
                    if (CommonMethod.isNetworkAvailable(this))
                        loginRetrofit(userName, password)
                    else CommonMethod.showToast(this)
                }
            }
            false
        }
    }

    private fun checkLogin(): Boolean {
        userName = edtUser.text.toString()
        password = edtPass.text.toString()
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Kiểm tra lại tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun loginRetrofit(userName: String, password: String) {
        var passwordHex = String(Hex.encodeHex(DigestUtils.md5(password)))
        val user = User(userName, passwordHex,null,null,null,null,null)
        val callLogin = api.login(user)
        callLogin.enqueue(this)
    }


    override fun onFailure(call: Call<User>, t: Throwable) {
        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {
        if (response.code() == 200) {
            token = response.body()?.token
            id = response.body()?.id
            role = response.body()?.role
            infoBySchoolId = response.body()?.infoBySchool
            studentId = response.body()?.studentId
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            MySharedPreferences.getInstance(this).setToken(token = token!!)
            MySharedPreferences.getInstance(this).setUser(userName = response.body()?.userName.toString())


        } else {
            Toast.makeText(this, "Kiểm tra lại tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show()
        }
    }
}

