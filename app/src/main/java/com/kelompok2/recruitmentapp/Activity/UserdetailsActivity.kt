package com.kelompok2.recruitmentapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompok2.recruitmentapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok2.recruitmentapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.*

class UserdetailsActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetails)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        userInfo()
    }

    private fun userInfo() {
        val jobsRef =
            FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.onee)
                        .into(profile_image)
                    full_name.setText(user!!.getFullname())



                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}