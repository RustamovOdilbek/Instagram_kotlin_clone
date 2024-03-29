package com.example.instagram.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagram.R
import com.example.instagram.manager.handler.AuthHandler
import com.example.instagram.manager.AuthManager
import com.example.instagram.manager.FirebaseConfig
import com.example.instagram.utils.DeepLink
import com.example.instagram.utils.Extensions.toast
import java.lang.Exception

/**
 * In SignInActivity, user can login using email, password
 */

class SignInActivity : BaseActivity() {
    val TAG = SignInActivity::class.java.simpleName

    val context: Context = this

    lateinit var et_email: EditText
    lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    private fun initViews() {

        val ll_background = findViewById<LinearLayout>(R.id.ll_background)
        val tv_instagram = findViewById<TextView>(R.id.tvInstagram)

        val tv_link = findViewById<TextView>(R.id.tv_link)
        DeepLink.retrieveLink(intent, tv_link)

        et_email = findViewById(R.id.et_email)
        et_password  =findViewById(R.id.et_password)
        val b_signin = findViewById<Button>(R.id.b_signin)
        b_signin.setOnClickListener {
            FirebaseConfig(ll_background, tv_instagram).updateConfig()
            Log.d(TAG, "initViews: ")
//            val email = et_email.text.toString()
//            val password = et_password.text.toString()
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                firebaseSignIn(email, password)
//            }
//
//            val str1 = reverse(email)
//            val str2 = reverse(password)
        }
        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }

       // FirebaseConfig(ll_background, tv_instagram).applyConfig()
    }

    private fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.signIn(email, password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                dismissLoading()
                toast(getString(R.string.str_signin_success))
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signin_failed))
            }
        })
    }

    private fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun reverse(string: String): String{
        val chars = string.toCharArray()

        var l = 0
        var b = string.length - 1
        while (l < b){
            val c = chars[l]
            chars[l] = chars[b]
            chars[b] = c
            l++
            b--
        }

        return String(chars)
    }

}