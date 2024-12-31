package com.example.passhelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passhelp.R
import com.example.passhelp.model.SecretsModel

class SecretsAdapter(
    private val list:List<SecretsModel>,
    private val onItemAreaClick: (SecretsModel) -> Unit,
    private val onItemOptionsClick: (SecretsModel) -> Unit,
    ):
    RecyclerView.Adapter<SecretsAdapter.SecretItemViewHolder>() {

    inner class SecretItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTxt: TextView = view.findViewById(R.id.iTitleSecret)
        val usernameTxt: TextView = view.findViewById(R.id.iUsernameSecret)
        val areaInfo:LinearLayout = view.findViewById(R.id.area_secret_info)
        val btnOption:ImageButton = view.findViewById(R.id.btn_secret_more)

        init {
            areaInfo.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemAreaClick(list[position])
                }
            }
            btnOption.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemOptionsClick(list[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_secrets, parent, false)
        return SecretItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SecretItemViewHolder, position: Int) {
        val secret = list[position]
        holder.titleTxt.text = secret.title
        holder.usernameTxt.text = secret.username
    }

}