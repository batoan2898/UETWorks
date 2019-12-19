package com.uet.uetworks.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uet.uetworks.R
import com.uet.uetworks.ui.main.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.graphics.Rect


class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment.newInstance()
    private val statusFragment = StatusFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    private val notificationFragment = NotificationFragment.newInstance()
    private val messageFragment = MessageFragment.newInstance()
    private lateinit var currentFragment: Fragment
    private lateinit var toolbar: ActionBar
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(homeFragment)
                    toolbar.title = getString(R.string.title_home)
                }
                R.id.nav_status -> {
                    loadFragment(statusFragment)
                    toolbar.title = getString(R.string.title_status)
                }
                R.id.nav_profile -> {
                    loadFragment(profileFragment)
                    toolbar.title = getString(R.string.title_profile)
                }
                R.id.nav_notification -> {
                    loadFragment(notificationFragment)
                    toolbar.title = getString(R.string.title_notification)
                }
                R.id.nav_message -> {
                    loadFragment(messageFragment)
                    toolbar.title = getString(R.string.title_message)
                }
            }
            true
        }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    private fun loadFragment(fragment: Fragment) {
        if (currentFragment == fragment)
            return
        supportFragmentManager.inTransaction {
            show(fragment)
            hide(currentFragment)
        }
        currentFragment = fragment

    }

    private fun addFragment(vararg fragment: Fragment) {
        supportFragmentManager.inTransaction {
            for (i in fragment.indices) {
                add(com.uet.uetworks.R.id.frame_container_main, fragment[i]).hide(fragment[i])
            }
            currentFragment = fragment[0]
            show(currentFragment)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
        if (ev?.getAction() === MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev?.getRawX() as Int, ev?.getRawY() as Int)) {
                    v.clearFocus()
                    val imm =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.uet.uetworks.R.layout.activity_main)
        toolbar = supportActionBar!!

        toolbar.title = getString(com.uet.uetworks.R.string.title_home)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        addFragment(
            homeFragment,
            statusFragment,
            profileFragment,
            notificationFragment,
            messageFragment
        )
    }
}