package com.example.passhelp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passhelp.R
import com.example.passhelp.adapters.GroupsAdapter
import com.example.passhelp.adapters.SecretsAdapter
import com.example.passhelp.dao.GroupDao
import com.example.passhelp.dao.SecretsDao
import com.example.passhelp.model.GroupModel
import com.example.passhelp.model.SecretsModel

class GroupFragment(idAuthor:Int) : Fragment() {

    private val idAuth = idAuthor;
    private lateinit var rclListGroup:RecyclerView;

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
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rclListGroup = view.findViewById(R.id.lsGroupSecrets)
        rclListGroup.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        loadMyGroups()
    }

    fun loadMyGroups(){
        val parentFragMan =  getParentFragmentManager()
        context?.let {
            val groupDao = GroupDao(it)
            val list = groupDao.filterGroups(GroupModel(
                0,"","",idAuth.toLong()
            ))
            rclListGroup.adapter = GroupsAdapter(list)
        }

    }

}