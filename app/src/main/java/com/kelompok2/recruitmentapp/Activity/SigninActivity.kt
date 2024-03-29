package com.kelompok2.recruitmentapp.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.kelompok2.recruitmentapp.ForgotPasswordActivity
import com.kelompok2.recruitmentapp.Helper.Constant
import com.kelompok2.recruitmentapp.Helper.PrefHelper
import com.kelompok2.recruitmentapp.HomeCandidate
import com.kelompok2.recruitmentapp.MainActivity9
import com.kelompok2.recruitmentapp.R
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*

class SigninActivity : AppCompatActivity() {

    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        prefHelper = PrefHelper(this)

        val window: Window = this@SigninActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@SigninActivity, R.color.giddy)


        signup_four.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        btnlogin.setOnClickListener {
            loginUser()
        }

        forgot_two_yes.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }


        wajuakwamba.setOnClickListener {
            startActivity(Intent(this, MainActivity9::class.java))
        }

    }

    private fun loginUser() {
        val email = email_text_two.text.toString()
        val password = password_text_two.text.toString()


        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password is required", Toast.LENGTH_LONG).show()


            else -> {
                val progressDialog = ProgressDialog(this@SigninActivity)
                progressDialog.setTitle("Sign In")
                progressDialog.setMessage("Please wait, this might take a while...")
                val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                    if (task.isSuccessful)
                    {
                        progressDialog.dismiss()
                        prefHelper.put( Constant.PREF_IS_LOGIN, true)
                        prefHelper.put( Constant.PREF_LEVEL, "Candidate")
                        val intent = Intent(this@SigninActivity, HomeCandidate::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message",Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progressDialog.dismiss()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null)
        {
            val intent = Intent(this@SigninActivity, HomeCandidate::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}