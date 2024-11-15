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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passhelp.adapters.SecretsAdapter
import com.example.passhelp.dao.SecretsDao
import com.example.passhelp.fragments.SecretDetailsBottomSheetFragment
import com.example.passhelp.fragments.SecretOptionsBottomSheetFragment
import com.example.passhelp.model.SecretsModel

class HomeActivity : AppCompatActivity() {
    private lateinit var rclVwSecrets:RecyclerView
    private lateinit var btnNewSecret:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(!isAuthenticated()){
            goToMain()
        }

        rclVwSecrets=this.findViewById(R.id.lsSecrets)
        btnNewSecret = this.findViewById(R.id.btnNew)

        rclVwSecrets.layoutManager = LinearLayoutManager(this)


        btnNewSecret.setOnClickListener{
            goToNew()
        }
    }
    fun goToNew(){
        val intent = Intent(this,ActivityNewSecret::class.java)
        startActivity(intent)
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
    fun buscarListaSecrets():MutableList<SecretsModel>{
        val sharedPreferences = getSharedPreferences("passhelper", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("userId",0);
        if(id==0){
            Toast.makeText(this,"Usuário não encontrado !",Toast.LENGTH_LONG).show()
        }else{
            val secretDao = SecretsDao(this);
            return secretDao.listAllByUserId(id);
        }
        return mutableListOf()

    }
    override fun onStart() {
        super.onStart()
        val listSecret = buscarListaSecrets()
        rclVwSecrets.adapter = SecretsAdapter(listSecret,
            onItemAreaClick = { secret:SecretsModel ->
                val secretDetailsBttSheet = SecretDetailsBottomSheetFragment(secret)
                secretDetailsBttSheet.show(supportFragmentManager,"SecretItemOptions")
            },
            onItemOptionsClick = { secret:SecretsModel ->
                val secretOptBottomSheet = SecretOptionsBottomSheetFragment(secret)
                secretOptBottomSheet.setOnEditClickListener {
                    showDialogConfirm(
                        onClickConfirm = {
                            Toast.makeText(this,"Editar",Toast.LENGTH_SHORT).show()
                        },
                        onClickCancel = {}
                    )
                }
                secretOptBottomSheet.setOnDeleteClickListener { id:Int ->
                    showDialogConfirm(
                        onClickConfirm = {
                            val secretDao = SecretsDao(this);
                            secretDao.deleteSecret(id)
                            this.onStart()
                            secretOptBottomSheet.dismiss()
                        },
                        onClickCancel = {}
                    )

                }
                secretOptBottomSheet.show(supportFragmentManager,"SecretItemOptions")
            })
    }

    private fun showDialogConfirm(
        onClickCancel:()->Unit,
        onClickConfirm:()->Unit
    ) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_decision)


        val confirmBtn:Button = dialog.findViewById(R.id.btn_decision_confirm)
        confirmBtn.setOnClickListener {
            dialog.dismiss()
            onClickConfirm()
        }

        val cancelBtn: Button = dialog.findViewById(R.id.btn_decision_cancel)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
            onClickCancel()
        }

        dialog.show()
    }
}