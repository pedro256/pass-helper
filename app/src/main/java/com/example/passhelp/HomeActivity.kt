package com.example.passhelp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passhelp.adapters.SecretsAdapter
import com.example.passhelp.dao.SecretsDao
import com.example.passhelp.fragments.GroupFragment
import com.example.passhelp.fragments.HomeFragment
import com.example.passhelp.fragments.ProfileFragment
import com.example.passhelp.fragments.SecretDetailsBottomSheetFragment
import com.example.passhelp.fragments.SecretOptionsBottomSheetFragment
import com.example.passhelp.model.SecretsModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.mainBottomNavigationView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.menu_group -> {
                    loadFragment(GroupFragment())
                    true
                }
                R.id.meun_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> {
                    loadFragment(HomeFragment())
                    true
                }
            }
        }

        if(!isAuthenticated()){
            goToMain()
        }


//        btnNewSecret = this.findViewById(R.id.btnNew)



//        btnNewSecret.setOnClickListener{
//            goToNew()
//        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFrameContainer,fragment)
        transaction.commit()
    }


    fun goToMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun isAuthenticated():Boolean{
        val sharedPreferences = getSharedPreferences("passhelper", Context.MODE_PRIVATE)
        val user = sharedPreferences.getInt("userId",0);
        val authenticated = sharedPreferences.getBoolean("isLoggedIn",false);
        if(user==0 || !authenticated){
            return false
        }
        return true
    }

//    override fun onStart() {
//        super.onStart()
//    }

}