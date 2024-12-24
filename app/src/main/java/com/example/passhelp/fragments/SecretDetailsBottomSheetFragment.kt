package com.example.passhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import com.example.passhelp.R
import com.example.passhelp.dao.SecretsDao
import com.example.passhelp.model.SecretsModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SecretDetailsBottomSheetFragment(
    secret:SecretsModel,
    editable:Boolean
) :BottomSheetDialogFragment(){

    private lateinit var txtTitle:EditText;
    private lateinit var txtUsername:EditText;
    private lateinit var txtPassword:EditText;
    private lateinit var txtDetails:EditText;
    private lateinit var btnConfirm:Button;

    private var sec = secret;
    private var isEditable = editable;

    private var onConfirmEdit: (() -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view =inflater.inflate(R.layout.frag_secret_details,container,false)

        txtTitle = view.findViewById(R.id.txtTitle);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtDetails = view.findViewById(R.id.txtDetails);
        btnConfirm = view.findViewById(R.id.btnConfirm);


        txtTitle.setText(this.sec.title);
        txtTitle.isClickable = isEditable;
        txtTitle.isFocusable = isEditable;
        txtTitle.isFocusableInTouchMode = isEditable;

        txtUsername.setText(this.sec.username);
        txtUsername.isClickable = isEditable;
        txtUsername.isFocusable = isEditable;
        txtUsername.isFocusableInTouchMode = isEditable;

        txtPassword.setText(this.sec.password)
        txtPassword.isClickable = isEditable;
        txtPassword.isFocusable = isEditable;
        txtPassword.isFocusableInTouchMode = isEditable;

        txtDetails.setText(this.sec.complement)
        txtDetails.isClickable = isEditable;
        txtDetails.isFocusable = isEditable;
        txtDetails.isFocusableInTouchMode = isEditable;

        btnConfirm.isVisible = isEditable;
        btnConfirm.isClickable = isEditable;
        btnConfirm.isFocusable = isEditable;
        btnConfirm.isFocusableInTouchMode = isEditable;

        btnConfirm.setOnClickListener{
            if(isEditable){
                val toUpdate = SecretsModel(
                    sec.id,
                    txtTitle.text.toString(),
                    txtUsername.text.toString(),
                    txtPassword.text.toString(),
                    txtDetails.text.toString(),
                    sec.userId
                )
                context?.let{
                    val secretDao = SecretsDao(it);
                    val id = secretDao.updateSecret(toUpdate);
                    onConfirmEdit?.invoke()
                    dismiss();
                }

            }
        }

        return view
    }

    fun setOnEditConfirmListener(listener : ()->Unit){
        this.onConfirmEdit = listener
    }
}