package com.uet.uetworks.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uet.uetworks.R
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import com.uet.uetworks.model.EmailVNU
import kotlinx.android.synthetic.main.activity_recover_pass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecoverPassActivity : AppCompatActivity(), Callback<EmailVNU> {
    lateinit var api: Api
    var emailVNU: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_pass)

        api = ApiBuilder.client?.create(Api::class.java)!!
        btnForgotPass.setOnClickListener {
            if (checkEmail()) {
                if (CommonMethod.isNetworkAvailable(this))
                    resetPassRetrofit(emailVNU)
                else CommonMethod.showAlert("Internet Connectivity Failure", this)
            }
        }
    }

    private fun checkEmail(): Boolean {
        emailVNU = edtEmail.text.toString()
        if (emailVNU.isEmpty()) {
            Toast.makeText(this, "Data Empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (emailVNU.indexOf(" ") > 0
            || emailVNU.indexOf("@") == -1
            || emailVNU.indexOf("@") != emailVNU.lastIndexOf("@")
            || emailVNU.lastIndexOf(".") == emailVNU.length - 1
        ) {
            Toast.makeText(this, "Data Error", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun resetPassRetrofit(email: String) {
        val emailReset = EmailVNU(email)
        val callResetPass = api.resetPass(emailReset)
        callResetPass.enqueue(this)
    }

    override fun onFailure(call: Call<EmailVNU>, t: Throwable) {
        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<EmailVNU>, response: Response<EmailVNU>) {
        Toast.makeText(this, response.code().toString(), Toast.LENGTH_SHORT).show()
        if (response.code() == 200) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }
}
