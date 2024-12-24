package com.example.passhelp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.passhelp.dao.UserDao
import com.example.passhelp.model.UserModel


class MainActivity : AppCompatActivity() {

    private lateinit var btnConnect:Button
    private lateinit var txtGotoRegstUser:TextView
    private lateinit var txtPassword:EditText;
    private lateinit var txtUsername:EditText;

    private lateinit var userDao:UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(isAuthenticated()){
            goToHome()
        }

        txtUsername = this.findViewById(R.id.edTextUsername)
        txtPassword = this.findViewById(R.id.edTextPassword)
        btnConnect =  this.findViewById(R.id.btnAuth)
        txtGotoRegstUser = this.findViewById(R.id.txt_rgst_user)

        userDao = UserDao(this)

        btnConnect.setOnClickListener{
            checkAuthentication()
        }
        txtGotoRegstUser.setOnClickListener{
            goToRgstUser()
        }
    }


    fun setCurrentAuth(id:Int) {
        val sharedPreferences = getSharedPreferences("passhelper", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", id)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }
    fun isAuthenticated():Boolean{
        val sharedPreferences = getSharedPreferences("passhelper", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn",false);
    }

    fun checkAuthentication(){

        var auth = userDao.authenticateUser(
            UserModel(0,"",
                txtUsername.text.toString(),
                txtPassword.text.toString()
            ))
        if(auth.message!=null && auth.message != ""){
            Toast.makeText(this,auth.message,Toast.LENGTH_LONG).show()

        }
        Log.i("informação",auth.toString())
        if(auth.authenticated){
            setCurrentAuth(auth.id)
            goToHome()
        }
    }


    fun goToHome(){
        var i =Intent(this,HomeActivity::class.java)
        startActivity(i)
    }
    fun goToRgstUser(){
        var i =Intent(this,RegisterUserActivity::class.java)
        startActivity(i)
    }
}