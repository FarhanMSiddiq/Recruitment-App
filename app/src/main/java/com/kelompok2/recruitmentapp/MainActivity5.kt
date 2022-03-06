package com.kelompok2.recruitmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main5.*

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected)
            {
                layoutDisconnected.visibility = View.GONE
                layoutconnected.visibility = View.VISIBLE
            }else{
                layoutconnected.visibility = View.GONE
                layoutDisconnected.visibility = View.VISIBLE
            }

        })

    }



}