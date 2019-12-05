package com.uet.uetworks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    private var handler: Handler = Handler()
    private val runnable = Runnable {
        handler.postDelayed({
            if (MySharedPreferences.getInstance(this).checkLogin()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }, 2000)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initActivity()
    }

    private fun initActivity() {
        var mSpinkit: Sprite = Wave()
        spinkit.setIndeterminateDrawable(mSpinkit)
        runnable.run()
    }

    public override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
