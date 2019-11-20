package com.uet.uetworks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.uet.uetworks.R
import kotlinx.android.synthetic.main.activity_recover_pass.*

class RecoverPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_pass)
        btnForgotPass.setOnClickListener {
            Toast.makeText(this, "Đang xây dựng", Toast.LENGTH_SHORT).show()
        }
    }
}
