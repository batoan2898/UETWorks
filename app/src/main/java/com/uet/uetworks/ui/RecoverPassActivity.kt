package com.uet.uetworks.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import kotlinx.android.synthetic.main.activity_recover_pass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecoverPassActivity : AppCompatActivity(), Callback<String> {
    lateinit var api: Api
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_pass)

        api = ApiBuilder.client?.create(Api::class.java)!!
        btnForgotPass.setOnClickListener {
            if (checkEmail()) {
                if (CommonMethod.isNetworkAvailable(this))
                    resetPassRetrofit(email)
                else CommonMethod.showAlert("Internet Connectivity Failure", this)
            }
        }
    }

    private fun checkEmail(): Boolean {
        email = edtEmail.text.toString()
        if (email.isEmpty()) {
            Toast.makeText(this, "Data Empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (email.indexOf(" ") > 0
            || email.indexOf("@") == -1
            || email.indexOf(".") != -1
            || email.indexOf("@") != email.lastIndexOf("@")
            || email.lastIndexOf(".") == email.length - 1
        ) {
            Toast.makeText(this, "Data Error", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun resetPassRetrofit(email: String) {
        val callResetPass = api.resetPass(email)
        callResetPass.enqueue(this)
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        Toast.makeText(this, "Reset Pass Failed!", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<String>, response: Response<String>) {
        var email: String? = response.body()
        Toast.makeText(this, response.code().toString(), Toast.LENGTH_SHORT).show()
        if (response.code() == 200) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Sai", Toast.LENGTH_SHORT).show()
        }
    }
}
