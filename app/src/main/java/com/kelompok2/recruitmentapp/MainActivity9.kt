package com.kelompok2.recruitmentapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kelompok2.recruitmentapp.Activity.SigninActivity
import com.kelompok2.recruitmentapp.Activity.SignupCompanyActivity
import com.kelompok2.recruitmentapp.Helper.Constant
import com.kelompok2.recruitmentapp.Helper.PrefHelper
import kotlinx.android.synthetic.main.activity_main9.*
import kotlinx.android.synthetic.main.activity_signin.*

class MainActivity9 : AppCompatActivity() {

    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        prefHelper = PrefHelper(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)


        login_candidate.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        btnlogin_three.setOnClickListener {
            loginUser()
        }

        signup_employer.setOnClickListener {
            startActivity(Intent(this, SignupCompanyActivity::class.java))
        }

        lupa_password.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun loginUser() {
        val email = email_text_two_three.text.toString()
        val password = password_text_two_three.text.toString()

        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password is required", Toast.LENGTH_LONG).show()


            else -> {
                val progressDialog = ProgressDialog(this@MainActivity9)
                progressDialog.setTitle("Sign In")
                progressDialog.setMessage("Please wait, this might take a while...")
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                    if (task.isSuccessful)
                    {
                        prefHelper.put( Constant.PREF_IS_LOGIN, true)
                        prefHelper.put( Constant.PREF_LEVEL, "Employer")
                        progressDialog.dismiss()
                        val intent = Intent(this@MainActivity9,HomeCompanyActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progressDialog.dismiss()
                    }
                }

            }
        }
    }
}