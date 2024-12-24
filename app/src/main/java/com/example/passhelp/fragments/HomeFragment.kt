package com.example.passhelp.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passhelp.ActivityNewSecret
import com.example.passhelp.R
import com.example.passhelp.adapters.SecretsAdapter
import com.example.passhelp.dao.SecretsDao
import com.example.passhelp.model.SecretsModel
import java.util.Objects

class HomeFragment : Fragment() {

    lateinit var rclVwSecrets: RecyclerView;
    lateinit var btnNewSecret: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNewSecret = view.findViewById(R.id.btnNewSecret)
        rclVwSecrets = view.findViewById(R.id.lsSecrets)
        rclVwSecrets.layoutManager = LinearLayoutManager(context)

        btnNewSecret.setOnClickListener{
            goToNew()
        }

    }

    fun goToNew(){
        val intent = Intent(context, ActivityNewSecret::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        //Toast.makeText(context,"Started",Toast.LENGTH_LONG).show()
        loadMySecrets()

    }

    fun loadMySecrets(){
        val parentFragMan =  getParentFragmentManager()
        context?.let {
            var scretList = buscarListaSecrets(it)

            rclVwSecrets.adapter = SecretsAdapter(scretList,
                onItemAreaClick = { secret:SecretsModel ->
                    val secretDetailsBttSheet = SecretDetailsBottomSheetFragment(secret)
                    secretDetailsBttSheet.show(parentFragMan,"SecretItemOptions")
                },
                onItemOptionsClick = { secret:SecretsModel ->
                    val secretOptBottomSheet = SecretOptionsBottomSheetFragment(secret)
                    secretOptBottomSheet.setOnEditClickListener {
                        showDialogConfirm(
                            it,
                            onClickConfirm = {
                                Toast.makeText(it,"Editar",Toast.LENGTH_SHORT).show()
                            },
                            onClickCancel = {}
                        )
                    }
                    secretOptBottomSheet.setOnDeleteClickListener { id:Int ->
                        showDialogConfirm(
                            it,
                            onClickConfirm = {
                                val secretDao = SecretsDao(it);
                                secretDao.deleteSecret(id)
                                this.onStart()
                                secretOptBottomSheet.dismiss()
                            },
                            onClickCancel = {}
                        )

                    }
                    secretOptBottomSheet.show(parentFragMan,"SecretItemOptions")
                })
        };
    }

    private fun showDialogConfirm(
        ctxt: Context,
        onClickCancel:()->Unit,
        onClickConfirm:()->Unit
    ) {
        val dialog = Dialog(ctxt)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_decision)


        val confirmBtn: Button = dialog.findViewById(R.id.btn_decision_confirm)
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

    fun buscarListaSecrets(ctxt:Context):MutableList<SecretsModel>{
        val sharedPreferences = ctxt.getSharedPreferences("passhelper", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("userId",0);
        if(id==0){
            Toast.makeText(context,"Usuário não encontrado !", Toast.LENGTH_LONG).show()
        }else{
            val secretDao = SecretsDao(ctxt)
            return secretDao.listAllByUserId(id);
        }

        return mutableListOf()

    }
}