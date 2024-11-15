package com.example.passhelp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.passhelp.dao.UserDao
import com.example.passhelp.model.UserModel

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var txtNome:EditText;
    private lateinit var txtUsername:EditText;
    private lateinit var txtPassword:EditText;
    private lateinit var btnRGst:Button;

    private lateinit var userDao: UserDao;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userDao = UserDao(this)

        txtNome =findViewById(R.id.txtName)
        txtUsername = findViewById(R.id.txtUsername)
        txtPassword = findViewById(R.id.txtPsswd)

        btnRGst = this.findViewById(R.id.btnRGst)
        btnRGst.setOnClickListener{
            registUser()
        }
    }

    fun registUser(){
        val id =userDao.insertUser(UserModel(
            0,
            txtNome.text.toString(),
            txtUsername.text.toString(),
            txtPassword.text.toString()
        ));
        if(id>0){
            Toast.makeText(this,"Usuario Cadastrado ",Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this,"Algo deu errado! ",Toast.LENGTH_LONG).show()
        }
    }
}