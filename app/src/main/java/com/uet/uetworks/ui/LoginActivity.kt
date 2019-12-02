package com.uet.uetworks.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class LoginActivity : AppCompatActivity(), Callback<User> {

    lateinit var api: Api
    var userName: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        api = ApiBuilder.client?.create(Api::class.java)!!
        btnLogin.setOnClickListener {
            if (checkLogin()) {
                if (CommonMethod.isNetworkAvailable(this))
                    loginRetrofit(userName, password)
                else CommonMethod.showAlert("Internet Connectivity Failure", this)
            }
        }
        tvForgotPass.setOnClickListener {
            val intent = Intent(this, RecoverPassActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkLogin(): Boolean {
        userName = edtUser.text.toString()
        password = edtPass.text.toString()
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Data Empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun loginRetrofit(userName: String, password: String) {
        var passwordHex = String(Hex.encodeHex(DigestUtils.md5(password)))
        var user = User(userName, passwordHex)
        val callLogin = api.login(user)
        callLogin.enqueue(this)
    }


    override fun onFailure(call: Call<User>, t: Throwable) {
        Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {
        var user: User? = response.body()
        Toast.makeText(this, response.code().toString(), Toast.LENGTH_SHORT).show()
        if (response.code() == 200) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }
}

