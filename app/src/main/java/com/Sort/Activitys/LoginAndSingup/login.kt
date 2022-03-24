package com.Sort.Activitys.LoginAndSingup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.Sort.Activitys.Home.Home
import com.Sort.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_singup.*
import kotlinx.android.synthetic.main.activity_splach.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class login : AppCompatActivity() {

    //userData
    var userUsername : String? = null
    var userPassword : String? = null


    //Database
    private val FirebaseUsedEmailAndUsernameReference :DatabaseReference  by lazy { Firebase.database.getReference("UsedEmailAndUsernameReference") }
    private val FirebaseUserAuthentication            :FirebaseAuth       by lazy { FirebaseAuth.getInstance() }
    var usedEmail  = ArrayList<String>()
    var usedUsername  = ArrayList<String>()


    //drawable
    private var loadingdrawable :  AnimationDrawable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(checkNetworkConnected()) {
            startActivityAnim()

            getLodingDrawable()


            login_username_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
                if(checkNetworkConnected()) {
                    userUsername = inputText.toString()
                    stopLoginUserHandler()

                    if (inputText.toString().isNotEmpty()) {
                        login_username_layout.hint = ""
                        setPasswordEnabel()
                        startLoginUserHandler()
                        checkNetworkConnected()
                    } else login_username_layout.hint = "Username"

                }

            }

            login_password_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
                if(checkNetworkConnected()) {
                    userPassword = inputText.toString()
                    stopLoginUserHandler()

                    if (inputText.toString().isNotEmpty()) {
                        login_password_layout.hint = ""
                        startLoginUserHandler()
                        checkNetworkConnected()
                    } else login_password_layout.hint = "Password"

                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        FirebaseUsedEmailAndUsernameReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("email").value != null) {
                    usedEmail = snapshot.child("email").value as ArrayList<String>
                }
                if (snapshot.child("username").value != null) {
                    usedUsername = snapshot.child("username").value as ArrayList<String>
                }
                checkNetworkConnected()
            }

            override fun onCancelled(error: DatabaseError) {
                if(checkNetworkConnected()){
                    Toast.makeText(this@login,"Data transfer error, please try again later.", Toast.LENGTH_LONG).show()
                }
            }

        })

    }

    private fun startActivityAnim(){
        if(checkNetworkConnected()) {
            login_logo_image.animate().alpha(1f).duration = 600
            login_username_background.animate().alpha(1f).duration = 800
            login_password_background.animate().alpha(0.4f).duration = 1400
            login_loding.animate().alpha(0.4f).duration = 1600
            login_password_layout.isEnabled = false
            login_username_layout.isEndIconVisible = false
            login_password_layout.isEndIconVisible = false
            checkNetworkConnected()
        }
    }


    private fun getLodingDrawable(){
        loadingdrawable = login_loding.drawable as AnimationDrawable?
    }

    private fun setLoding(loding: Boolean){
        if(checkNetworkConnected()) {
            if (loding) {
                loadingdrawable?.start()
                login_loding.animate().alpha(1f).duration = 600
            } else {
                loadingdrawable?.stop()
                login_loding.animate().alpha(0.4f).duration = 600
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPasswordEnabel() {
        if(checkNetworkConnected()) {
            if (usedUsername.isNotEmpty()) {
                login_username_layout.isEndIconVisible = usedUsername.contains(userUsername)
                if (usedUsername.contains(userUsername)) {
                    login_password_background.animate().alpha(1f).duration = 800
                    login_password_background.background = getDrawable(R.drawable.text_filed_background)
                    login_password_icon.setImageDrawable(getDrawable(R.drawable.ic_password))
                    login_password_layout.isEnabled = true
                } else {
                    login_password_background.animate().alpha(0.4f).duration = 800
                    login_password_background.background = getDrawable(R.drawable.text_filed_background_dark)
                    login_password_icon.setImageDrawable(getDrawable(R.drawable.ic_password_dark))
                    login_password_layout.isEnabled = false
                    setLoding(false)
                }
            }
        }
    }

    //hide keyboard
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
        checkNetworkConnected()
    }
    fun Context.hideKeyboard(view: View) {
        if(checkNetworkConnected()) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun LoginUser(){
        if(checkNetworkConnected()) {
            if (userUsername != null && userPassword != null)
                if (usedUsername.contains(userUsername)) {
                    if(checkNetworkConnected()) {
                        FirebaseUserAuthentication.signInWithEmailAndPassword(usedEmail
                                .get(usedUsername.indexOf(userUsername)), userPassword)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        hideKeyboard()
                                        login_password_layout.isEndIconVisible = true
                                        login_username_layout.isEnabled = false
                                        login_password_layout.isEnabled = false
                                        setLoding(true)
                                        Handler().postDelayed({
                                            startActivity(
                                                    Intent(this@login, Home::class.java).putExtra("username", userUsername))
                                            finish()
                                        }, 2000)
                                    }
                                }
                    }
                }
        }
    }

    // Login User Loding
    private var LoginUserHandler = Handler()
    private var LoginUserRunnable = Runnable {
        LoginUser()
    }
    private fun startLoginUserHandler() {
        if(checkNetworkConnected()) {
            LoginUserHandler.postDelayed(LoginUserRunnable, 500)
        }
    }
    private fun stopLoginUserHandler() {
        LoginUserHandler.removeCallbacks(LoginUserRunnable)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkNetworkConnected() : Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if(!(cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected)) {
            setContentView(R.layout.internt_dialog)

            val internet_bt_view : Button = findViewById(R.id.internet_dialog_retry_button)
            val internet_icon_view : ImageView = findViewById(R.id.internet_dialog_internet_loding)
            val internet_loadingdrawable = internet_icon_view.drawable as AnimationDrawable?

            internet_bt_view.setOnClickListener {
                internet_bt_view.visibility = View.GONE
                internet_icon_view.visibility = View.VISIBLE
                internet_loadingdrawable?.start()

                Handler().postDelayed({
                    if(cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected) {
                        startActivity(Intent(this, login::class.java))
                        finish()
                    }
                    else {
                        internet_loadingdrawable?.stop()
                        internet_icon_view.visibility = View.GONE
                        internet_bt_view.visibility = View.VISIBLE
                    }
                }, 2000)

            }

        }
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

}
