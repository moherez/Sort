package com.Sort.Activitys.Home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.Sort.Activitys.Home.HomeFragments.WorkplacesFragment
import com.Sort.Activitys.Home.HomeFragments.NotificationsFragment
import com.Sort.Activitys.Profile.Profile
import com.Sort.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val workplacesFragment = WorkplacesFragment()
    private val notificationsFragment = NotificationsFragment()




    //Database
    private val FirebaseUsedEmailAndUsernameReference : DatabaseReference by lazy { Firebase.database.getReference("UsedEmailAndUsernameReference") }
    private val FirebaseUsersMainCollection           : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}
    private val FirebaseUserAuthentication            : FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val FirebaseUsersMedieStorge              : StorageReference?  by lazy { FirebaseStorage.getInstance().reference }




    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        setSupportActionBar(new_workplace_toolbar)
        supportActionBar?.title = ""



        setFragment(workplacesFragment)
        home_bottom_navigation_view.setOnNavigationItemSelectedListener(this@Home)



        home_profile_image.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }




    }


    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()
  /*
        var isFirestLoginUserName : String? = intent.getStringExtra("username")
        val preference=getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor=preference.edit()

        FirebaseUsersMainCollection.collection("Users")
                .document(
                        (if(isFirestLoginUserName != null) isFirestLoginUserName
                        else preference.getString("username","erorr") ).toString())
                .addSnapshotListener { snapshot, e ->

                }
  */
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.buttom_navigation_workplaces -> {
                setFragment(workplacesFragment)
                return true
            }

            R.id.buttom_navigation_notifications -> {
                setFragment(notificationsFragment)
                return true
            }

            else -> {
                return false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fr = supportFragmentManager.beginTransaction()
        fr.replace(R.id.home_coordinatorlayout_main,fragment)
        fr.commit()
    }

}