package com.example.passhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.passhelp.R
import com.example.passhelp.model.SecretsModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SecretDetailsBottomSheetFragment(
    secret:SecretsModel
) :BottomSheetDialogFragment(){

    private var sec = secret;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view =inflater.inflate(R.layout.frag_secret_details,container,false)

        view.findViewById<EditText>(
            R.id.txtTitle
        ).setText(this.sec.title)
        view.findViewById<EditText>(
            R.id.txtUsername
        ).setText(this.sec.username)
        view.findViewById<EditText>(
            R.id.txtPassword
        ).setText(this.sec.password)
        return view
    }
}