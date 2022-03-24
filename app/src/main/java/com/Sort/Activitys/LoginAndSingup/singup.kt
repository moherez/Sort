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
import Model.User
import com.Sort.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_singup.*

@Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class singup : AppCompatActivity() {


    // user data
    private var userEmail     :String? = null
    private var userUserName  :String? = null
    private var userPassword  :String? = null
    private var userFirstName :String? = null
    private var userLastName  :String? = null
    private var userFullName  :String? = null


    //Database
    private val FirebaseUsedEmailAndUsernameReference :DatabaseReference  by lazy { Firebase.database.getReference("UsedEmailAndUsernameReference") }
    private val FirebaseUsersMainCollection           :FirebaseFirestore  by lazy { FirebaseFirestore.getInstance()}
    private val FirebaseUserAuthentication            :FirebaseAuth       by lazy { FirebaseAuth.getInstance() }
    private val FirebaseUsersMedieStorge              :StorageReference?  by lazy { FirebaseStorage.getInstance().reference }
    var usedEmail  = ArrayList<String>()
    var usedUsername  = ArrayList<String>()


    //drawable
    private var loadingdrawable :  AnimationDrawable? = null


    @SuppressLint("UseCompatLoadingForColorStateLists", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

if(checkNetworkConnected()){
        getLodingDrawable()

        startActivityAnim()

        giveInitialValueForUserData()

        singup_email_layout.editText?.doOnTextChanged { inputText, _, _, _ ->

            if(checkNetworkConnected()){
                setUsernameEnabled(isEmail(inputText.toString()))
                userEmail = inputText.toString()

                if (!isEmail(inputText.toString())) {
                    setPasswordEnabled(false)
                    setSingupEnabled(false)
                } else {
                    setPasswordEnabled(isUsername(userUserName.toString()))
                    setSingupEnabled(isPassword(userPassword.toString()))
                }
            }

        }

        singup_username_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()){
                setPasswordEnabled(isUsername(inputText.toString()))
                userUserName = inputText.toString()

                if (!isUsername(inputText.toString())) setSingupEnabled(false)
                else setSingupEnabled(isPassword(userPassword.toString()))
                checkNetworkConnected()
            }

        }

        singup_password_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()){
                setSingupEnabled(isPassword(inputText.toString()))
                userPassword = inputText.toString()
                checkNetworkConnected()
            }
        }

        singup_singup.setOnClickListener {
            if(checkNetworkConnected()) {
                switchToEnterNameScreen()
            }
        }

        singup_first_name_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                userFirstName = inputText.toString()

                if (inputText.toString().isEmpty()) {
                    singup_first_name_layout.hint = "First name.."
                    singup_first_name_background.background = getDrawable(R.drawable.text_filed_background_dark_2)
                } else {
                    singup_first_name_layout.hint = ""
                    singup_first_name_background.background = getDrawable(R.drawable.text_filed_background)
                }
                saveName()

            }
        }

        singup_last_name_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                userLastName = inputText.toString()

                if (inputText.toString().isEmpty()) {
                    singup_last_name_layout.hint = "Last name.."
                    singup_last_name_background.background = getDrawable(R.drawable.text_filed_background_dark_2)
                } else {
                    singup_last_name_layout.hint = ""
                    singup_last_name_background.background = getDrawable(R.drawable.text_filed_background)
                }
                saveName()
            }

        }

        singup_user_image.setOnClickListener {
            if(checkNetworkConnected()) {
                pickUserImage()
            }
        }

        singup_end_user_image.setOnClickListener {
            if(checkNetworkConnected()) {
                pickUserImage()
            }
        }

        singup_end_name_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                if (!inputText.isNullOrEmpty()) singup_end_name_layout.hint = ""
                else singup_end_name_layout.hint = "Name"
            }


        }

        singup_end_username_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                if (!inputText.isNullOrEmpty()) singup_end_username_layout.hint = ""
                else singup_end_username_layout.hint = "Username"
            }

        }

        singup_end_email_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                if (!inputText.isNullOrEmpty()) singup_end_email_layout.hint = ""
                else singup_end_email_layout.hint = "Email"
            }

        }

        singup_end_password_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                if (!inputText.isNullOrEmpty()) singup_end_password_layout.hint = ""
                else singup_end_password_layout.hint = "Password"
            }

        }

        singup_end_company_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                if (!inputText.isNullOrEmpty()) singup_end_company_layout.hint = ""
                else singup_end_company_layout.hint = "Company"
            }

        }

        singup_end_description_layout.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(checkNetworkConnected()) {
                if (!inputText.isNullOrEmpty()) singup_end_description_layout.hint = ""
                else singup_end_description_layout.hint = "Description .."
            }

        }

        singup_done.setOnClickListener {

            if(checkNetworkConnected()) {
                if (!isName(_singup_end_name.text.toString())) showErrorDialode("The name must contain letters or symbols only.")
                else if (!isUsername(_singup_end_username.text.toString())) {
                    if (usedUsername.contains(_singup_end_username.text.toString())) showErrorDialode("Username is used.")
                    else showErrorDialode("Username must contain letters and numbers only, not begin with a number.")
                } else if (!isEmail(_singup_end_email.text.toString())) {
                    if (usedEmail.contains(_singup_end_email.text.toString())) showErrorDialode("Email is used.")
                    else showErrorDialode("Please add a valid email.")
                } else if (!isPassword(_singup_end_password.text.toString())) showErrorDialode("Password must be longer than 5 characters.")

//                 else createUser()

            }


        }
    }
    }

    override fun onStart() {
        super.onStart()
        if(checkNetworkConnected()) {
            FirebaseUsedEmailAndUsernameReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child("email").value != null) {
                        usedEmail = snapshot.child("email").value as ArrayList<String>
                    }
                    if (snapshot.child("username").value != null) {
                        usedUsername = snapshot.child("username").value as ArrayList<String>
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    if(checkNetworkConnected()){
                        Toast.makeText(this@singup,"Data transfer error, please try again later.",Toast.LENGTH_LONG).show()
                    }
                }

            })
        }

    }

    private fun startActivityAnim(){
        singup_logo_image.animate().alpha(1f).duration = 600
        singup_email_background.animate().alpha(1f).duration = 800
        singup_username_background.animate().alpha(0.4f).duration = 1400
        singup_password_background.animate().alpha(0.4f).duration = 1800
        singup_singup.animate().alpha(0.4f).duration = 2400
        singup_username_layout.isEnabled = false
        singup_password_layout.isEnabled = false
        singup_singup.isClickable = false
    }

    private fun giveInitialValueForUserData(){
        userEmail = singup_email_layout.editText?.text.toString()
        userUserName = singup_username_layout.editText?.text.toString()
        userPassword = singup_password_layout.editText?.text.toString()
    }

    private fun getLodingDrawable(){
        loadingdrawable = singup_loding.drawable as AnimationDrawable?
    }

    private fun isEmail(text: String) :Boolean{
        if (text.isEmpty()) {
            singup_email_layout.hint = "Email"
            return false
        }

        else if (text.contains('@') && text.contains('.')) {

            return if (text[0] == '@' || text[0] == '.' || text[text.length - 1] == '.' ||
                    text.lastIndexOf('@') > text.lastIndexOf('.') || text.contains(" ")) false
            else if ((text.indexOf('@') == (text.length-1)) || text[text.indexOf('@') + 1] == '.') false
            else {
                singup_email_layout.hint = ""
                !isUsedEmail(text)

            }
        }
        else return false
    }

    private fun isUsername(text: String) :Boolean{
        if(text.isEmpty()){
            singup_username_layout.hint = "Username"
            return false
        }
        else if(text.contains(" ") || text.length<4) return false
        else if(!text.get(0).isLetter()) return false
        else{
            for(i in text) if(!i.isLetterOrDigit() || i.isUpperCase()) return false
            singup_username_layout.hint = ""
            return !isUsedUsername(text)
        }


    }

    private fun isPassword(text: String) :Boolean{
        if(text.isEmpty()) singup_password_layout.hint = "Password"
        else singup_password_layout.hint = ""
        return text.length > 4
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUsernameEnabled(enabled: Boolean){
        if(enabled){
            singup_username_background.animate().alpha(1f).duration = 800
            singup_username_background.background = getDrawable(R.drawable.text_filed_background)
            singup_username_icon.setImageDrawable(getDrawable(R.drawable.ic_user))
            singup_username_layout.isEnabled = true
        }
        else{
            singup_username_background.animate().alpha(0.4f).duration = 800
            singup_username_background.background = getDrawable(R.drawable.text_filed_background_dark)
            singup_username_icon.setImageDrawable(getDrawable(R.drawable.ic_user_dark))
            singup_username_layout.isEnabled = false
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPasswordEnabled(enabled: Boolean){
        if(enabled){
            singup_password_background.animate().alpha(1f).duration = 800
            singup_password_background.background = getDrawable(R.drawable.text_filed_background)
            singup_password_icon.setImageDrawable(getDrawable(R.drawable.ic_password))
            singup_password_layout.isEnabled = true
        }
        else{
            singup_password_background.animate().alpha(0.4f).duration = 800
            singup_password_background.background = getDrawable(R.drawable.text_filed_background_dark)
            singup_password_icon.setImageDrawable(getDrawable(R.drawable.ic_password_dark))
            singup_password_layout.isEnabled = false
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun setSingupEnabled(enabled: Boolean){
        if(enabled){
            singup_singup.backgroundTintList = this.resources.getColorStateList(R.color.dark_green)
            singup_singup.setTextColor(this.resources.getColorStateList(R.color.light_text_green))
            singup_singup.isClickable = true
            singup_singup.animate().alpha(1f).duration = 800
        }
        else{
            singup_singup.backgroundTintList = this.resources.getColorStateList(R.color.light_green_background)
            singup_singup.setTextColor(this.resources.getColorStateList(R.color.dark_text_green))
            singup_singup.isClickable = false
            singup_singup.animate().alpha(0.4f).duration = 800
        }
    }

    private fun isUsedEmail(text: String) :Boolean {
        if(usedEmail.isNotEmpty() && usedEmail.contains(text)) Toast.makeText(this@singup,"The email is used.",Toast.LENGTH_LONG).show()
        return usedEmail.isNotEmpty() && usedEmail.contains(text)
    }

    private fun setAsUsedEmail(){
        usedEmail.add(_singup_end_email.text.toString())
        FirebaseUsedEmailAndUsernameReference.child("email").setValue(usedEmail)
    }

    private fun isUsedUsername(text: String) :Boolean {
        if(usedUsername.isNotEmpty() && usedUsername.contains(text)) Toast.makeText(this@singup,"The uername is used.",Toast.LENGTH_LONG).show()
        return usedUsername.isNotEmpty() && usedUsername.contains(text)
    }

    private fun setAsUsedUsername(){
        usedUsername.add(_singup_end_username.text.toString())
        FirebaseUsedEmailAndUsernameReference.child("username").setValue(usedUsername)
    }

    private fun switchToEnterNameScreen(){
        singup_logo_image.animate().alpha(0f).duration = 500
        if(checkNetworkConnected()) {
            Handler().postDelayed({
                singup_email_background.animate().alpha(0f).duration = 500
            }, 200)

            Handler().postDelayed({
                singup_username_background.animate().alpha(0f).duration = 500
            }, 400)

            Handler().postDelayed({
                singup_password_background.animate().alpha(0f).duration = 500
            }, 600)

            Handler().postDelayed({
                singup_singup.animate().alpha(0f).duration = 500
            }, 800)

            Handler().postDelayed({
                singup_main_user_data_layout.visibility = View.GONE
                singup_firesandlastname_relative_background.visibility = View.VISIBLE
                singup_first_name_background.animate().alpha(1f).duration = 500
            }, 1000)

            Handler().postDelayed({
                singup_last_name_background.animate().alpha(1f).duration = 500
            }, 1200)
        }

    }

    private fun isName(text: String) :Boolean{
        var isName = true
        if(text.isEmpty()){
            isName = false
        }
        else for(i in text) if (!i.isLetter() && !i.isWhitespace()) isName = false
        return isName
    }

    private fun setFullName(){
        userFullName = userFirstName + " " + userLastName
    }

    private fun setLoding(loding: Boolean){
        if (loding) loadingdrawable?.start()
        else loadingdrawable?.stop()
    }


    //hide keyboard
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }
    fun Context.hideKeyboard(view: View) {
        if(checkNetworkConnected()) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    //toImageUploadScreean
    private var toImageUploadScreeanHandler = Handler()
    private var toImageUploadScreeanRunnable = Runnable {

        hideKeyboard()
        Handler().postDelayed({
            setFullName()
            switchToImageUploadScreean()
        }, 3000)


    }
    private fun saveName(){
        if(checkNetworkConnected()) {
            fun start() {
                toImageUploadScreeanHandler.postDelayed(toImageUploadScreeanRunnable, 2000)
            }

            fun stop() {
                toImageUploadScreeanHandler.removeCallbacks(toImageUploadScreeanRunnable)
            }

            stop()
            setLoding(false)

            if (userFirstName != null && userLastName != null) {
                if (isName(userFirstName!!) && isName(userLastName!!)) {
                    start()
                    singup_loding.animate().alpha(1f).duration = 500
                    setLoding(true)

                } else {
                    stop()
                    singup_loding.animate().alpha(0f).duration = 500
                    setLoding(false)
                }

            }
        }
    }

    private fun switchToImageUploadScreean(){
        singup_loding.animate().alpha(0f).duration = 1000
        setLoding(false)

        Handler().postDelayed({
            singup_first_name_background.animate().alpha(0f).duration = 500
        }, 200)

        Handler().postDelayed({
            singup_last_name_background.animate().alpha(0f).duration = 500
        }, 400)

        Handler().postDelayed({
            singup_first_name_background.visibility = View.GONE
            singup_last_name_background.visibility = View.GONE
            singup_loding.visibility = View.GONE
            singup_logo_image.visibility = View.GONE
            singup_user_image.visibility = View.VISIBLE
            singup_user_image.animate().alpha(1f).duration = 1000
        }, 950)

        Handler().postDelayed({
            singup_tv_add_photo.visibility = View.VISIBLE
            singup_tv_add_photo.animate().alpha(1f).duration = 1000
        }, 1100)

    }

    private fun pickUserImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(checkNetworkConnected()) {
            if (requestCode == 3 && resultCode == RESULT_OK) {
                if(checkNetworkConnected()) {
                    setLoding(true)
                    val uriImage = data?.data
                    singup_tv_add_photo.animate().alpha(0f).translationY(100f).duration = 500

                    Handler().postDelayed({
                        singup_tv_add_photo.visibility = View.GONE
                        singup_loding.visibility = View.VISIBLE
                        singup_loding.animate().alpha(1f).duration = 500

                    }, 500)

                    val filepath = FirebaseUsersMedieStorge?.child(userUserName!!)
                    if (uriImage != null) {
                        if(checkNetworkConnected()) {
                            singup_user_image.isClickable = false
                            singup_user_image_image.setImageURI(uriImage)
                            singup_end_user_image_image.setImageURI(uriImage)
                            filepath?.putFile(uriImage)?.addOnCompleteListener() {
                                if (it.isSuccessful) switchToOverViewScreen()
                                else {
                                    if(checkNetworkConnected()){
                                        Toast.makeText(this@singup,"Data transfer error, please try again later.",Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun switchToOverViewScreen() {
        if(checkNetworkConnected()) {
            singup_user_image.animate().alpha(0f).translationY(200f).duration = 500
            singup_loding.animate().alpha(0f).translationY(200f).duration = 500

            _singup_end_name.setText(userFullName)
            _singup_end_username.setText(userUserName)
            _singup_end_email.setText(userEmail)
            _singup_end_password.setText(userPassword)

            Handler().postDelayed({
                singup_user_image.visibility = View.GONE
                singup_loding.visibility = View.GONE
                singup_firesandlastname_relative_background.visibility = View.GONE
                singup_overview_layout.visibility = View.VISIBLE
                singup_end_user_image.animate().alpha(1f).duration = 500
            }, 500)
            Handler().postDelayed({
                singup_done.animate().alpha(1f).duration = 500
            }, 600)
            Handler().postDelayed({
                singup_end_name_background.animate().alpha(1f).duration = 500

            }, 700)
            Handler().postDelayed({
                singup_end_username_background.animate().alpha(1f).duration = 500

            }, 800)
            Handler().postDelayed({
                singup_end_email_background.animate().alpha(1f).duration = 500
            }, 900)
            Handler().postDelayed({
                singup_end_password_background.animate().alpha(1f).duration = 500
            }, 1000)
            Handler().postDelayed({
                singup_end_company_background.animate().alpha(1f).duration = 500
            }, 1200)
            Handler().postDelayed({
                singup_end_description_background.animate().alpha(1f).duration = 500
            }, 1300)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showErrorDialode(message :String){

        MaterialAlertDialogBuilder(this)
                .setTitle("Erorr:")
                .setMessage(message)
                .setNegativeButton("Ok") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
    }
//
//    private fun createUser(){
//
//        if(checkNetworkConnected()) {
//            FirebaseUserAuthentication.createUserWithEmailAndPassword(_singup_end_email.text.toString(), _singup_end_password.text.toString()).addOnCompleteListener {
//                if (it.isSuccessful) {
//                    FirebaseUserAuthentication.currentUser.sendEmailVerification()
//
//                    val newUser = User(
//                    _singup_end_name.text.toString(),
//                    _singup_end_username.text.toString(),
//                    _singup_end_email.text.toString(),
//                    " gs://sort-android.appspot.com/$userUserName"
//                    )
//
//                    if(_singup_end_company.text.toString().isNotEmpty()) newUser.company = _singup_end_company.text.toString()
//                    if(_singup_end_description.text.toString().isNotEmpty()) newUser.description = _singup_end_description.text.toString()
//
//                   FirebaseUsersMainCollection.
//                   collection("Users").
//                   document(_singup_end_username.text.toString()).
//                   set(newUser).addOnCompleteListener {
//                       if(it.isSuccessful) switchToHomeScreen()
//                       else{
//                           if(checkNetworkConnected()) Toast.makeText(this@singup,"Data transfer error, please try again later.",Toast.LENGTH_LONG).show()
//                       }
//                   }
//
//
//
//                }
//                else {
//                    if(checkNetworkConnected()){
//                        Toast.makeText(this@singup,"Data transfer error, please try again later.",Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//            setAsUsedEmail()
//            setAsUsedUsername()
//        }
//    }

    private fun switchToHomeScreen(){
        startActivity(Intent(this@singup, Home::class.java).putExtra("username", _singup_end_username.text.toString()))
        finish()
    }



    private fun checkNetworkConnected() : Boolean {
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
                        startActivity(Intent(this, singup::class.java))
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