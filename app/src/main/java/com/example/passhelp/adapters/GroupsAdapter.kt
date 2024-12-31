package com.example.passhelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passhelp.R
import com.example.passhelp.adapters.SecretsAdapter.SecretItemViewHolder
import com.example.passhelp.model.GroupModel

class GroupsAdapter(
    private val list:List<GroupModel>,
):RecyclerView.Adapter<GroupsAdapter.GroupItemViewHolder>(){

    inner class GroupItemViewHolder(view:View):RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.txtName);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group,parent,false)
        return GroupItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) {
        val group = list[position];
        holder.name.text = group.name;
    }
}