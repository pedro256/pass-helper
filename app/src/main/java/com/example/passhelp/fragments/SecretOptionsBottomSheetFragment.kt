package com.example.passhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.passhelp.R
import com.example.passhelp.model.SecretsModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SecretOptionsBottomSheetFragment(secret:SecretsModel): BottomSheetDialogFragment() {

    private var secret = secret;
    private var onEditClicked: (() -> Unit)? = null
    private var onDeleteClicked: ((id:Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_secret_options, container, false)

        // Configurações dos botões
        view.findViewById<Button>(R.id.btn_edit_secret).setOnClickListener {
            onEditClicked?.invoke()
            dismiss()
        }

        view.findViewById<Button>(R.id.btn_delete_secret).setOnClickListener {
            onDeleteClicked?.invoke(secret.id)
        }

        return view
    }

    fun setOnEditClickListener(listener: () -> Unit) {
        onEditClicked = listener
    }

    fun setOnDeleteClickListener(listener: (id:Int) -> Unit) {
        onDeleteClicked = listener
    }
}