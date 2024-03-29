package com.kelompok2.recruitmentapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.kelompok2.recruitmentapp.*

class NavigationActivity : AppCompatActivity() {
    lateinit var secondFragment: SecondFragment
    lateinit var lastFragment: LastFragment
    lateinit var notificationsFragment: NotificationsFragment
    lateinit var messagesFragment: MessagesFragment
    lateinit var firstFragment: FirstFragment
    lateinit var hustleFragment: HustleFragment
    lateinit var savedFragment: SavedFragment
    lateinit var latestinfoFragment: LatestinfoFragment
    lateinit var myFragment: MyFragment
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mNavigationView: NavigationView
    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.btn_nav)

        val window:Window = this@NavigationActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@NavigationActivity, R.color.giddy)

        lastFragment = LastFragment()
        supportFragmentManager
            .beginTransaction().replace(R.id.frame_layout, lastFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.home -> {
                    lastFragment = LastFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, lastFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

                R.id.notifications -> {
                    myFragment = MyFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, myFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }


                R.id.messages -> {
                    savedFragment = SavedFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, savedFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }


                R.id.jobs -> {
                    firstFragment = FirstFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, firstFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }


            }
            true
        }


    }
}