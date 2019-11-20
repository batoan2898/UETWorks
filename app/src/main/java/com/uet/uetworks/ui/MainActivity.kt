package com.uet.uetworks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uet.uetworks.R
import com.uet.uetworks.ui.main.*
import kotlinx.android.synthetic.main.activity_main.*

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
                }
                R.id.nav_status -> {
                    loadFragment(statusFragment)
                }
                R.id.nav_profile -> {
                    loadFragment(profileFragment)
                }
                R.id.nav_notification -> {
                    loadFragment(notificationFragment)
                }
                R.id.nav_message -> {
                    loadFragment(messageFragment)
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
                add(R.id.frame_container_main, fragment[i]).hide(fragment[i])
            }
            currentFragment = fragment[0]
            show(currentFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar!!
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