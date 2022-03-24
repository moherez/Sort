package com.Sort.Activitys.Splach

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.Sort.Activitys.Home.Home
import com.Sort.Activitys.LoginAndSingup.login
import com.Sort.Activitys.LoginAndSingup.singup
import com.Sort.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splach.*


@Suppress("DEPRECATION")
class SplachActivity : AppCompatActivity() {


    private var loadingdrawable                       :AnimationDrawable?    = null
    private val FirebaseUserAuthentication            :FirebaseAuth          by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)


        //loading animation
        loadingdrawable = splach_loding.drawable as AnimationDrawable?
        loadingdrawable?.start()

        //go to login screen
        splach_login.setOnClickListener {
            startActivity(Intent(this@SplachActivity, login::class.java))
        }

        //go to sing up screen
        splach_singup.setOnClickListener {
            startActivity(Intent(this@SplachActivity, singup::class.java))
        }

}

    private fun singUpOrLoginAnim(){
        Handler().postDelayed({
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.main_background)
            splach_logo_image.animate().translationY(-500f).duration = 1000
            spalash_bakground_layout.animate().alpha(0.0f).duration = 1000
            splach_loding.animate().translationY(-50f).alpha(0.0f).duration = 500
        }, 2000)

        Handler().postDelayed({
            window.navigationBarColor = ContextCompat.getColor(this, R.color.main_background)
            splach_singup.animate().alpha(1f).duration = 800
        }, 2500)

        Handler().postDelayed({
            splach_login.animate().alpha(1f).duration = 800
        }, 2600)
    }

    private fun goToHomeScreen(){
        Handler().postDelayed({
            loadingdrawable?.stop()
            startActivity(Intent(this@SplachActivity, Home::class.java))
            finish()
        }, 1500)
    }

    override fun onStart() {
        super.onStart()

        //check current user
        if(FirebaseUserAuthentication.currentUser == null) singUpOrLoginAnim()
        else goToHomeScreen()
    }


    }

