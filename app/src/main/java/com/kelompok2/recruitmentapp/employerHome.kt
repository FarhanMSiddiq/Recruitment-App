package com.kelompok2.recruitmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelompok2.recruitmentapp.Activity.JobpostingActivity
import com.kelompok2.recruitmentapp.Activity.LatestMessagesActivity
import kotlinx.android.synthetic.main.fragment_employerhome.view.*

class employerHome : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_employerhome, container, false)


        view.posthustleme.setOnClickListener {
            startActivity(Intent(context, JobpostingActivity::class.java))
        }

        view.optiven.setOnClickListener {
            startActivity(Intent(context, LatestMessagesActivity::class.java))
        }

        return view
    }


}