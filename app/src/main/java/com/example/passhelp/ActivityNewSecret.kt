package com.example.passhelp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.passhelp.dao.SecretsDao
import com.example.passhelp.model.SecretsModel

class ActivityNewSecret : AppCompatActivity() {
    private lateinit var txtTitle:EditText
    private lateinit var txtUsername:EditText
    private lateinit var txtPassword:EditText
    private lateinit var txtComplement:EditText
    private lateinit var btnCreate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_secret)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtTitle = this.findViewById(R.id.txtTitle)
        txtUsername = this.findViewById(R.id.txtUsername)
        txtPassword = this.findViewById(R.id.txtPassword)
        txtComplement = this.findViewById(R.id.txtComplement)
        btnCreate = this.findViewById(R.id.btnRegist)

        btnCreate.setOnClickListener{
            createNewSecret()
        }

    }

    fun createNewSecret(){
        val sharedPreferences = getSharedPreferences("passhelper", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("userId",0);

        val secretDao = SecretsDao(this)

        val save = SecretsModel(
            0,
            txtTitle.text.toString(),
            txtUsername.text.toString(),
            txtPassword.text.toString(),
            txtComplement.text.toString(),
            id
        );

        val newId = secretDao.createSecret(save)

        if(newId>0){
            Toast.makeText(this,"Usuário Criado !",Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this,"Algo deu errado !",Toast.LENGTH_LONG).show()
        }

    }
}