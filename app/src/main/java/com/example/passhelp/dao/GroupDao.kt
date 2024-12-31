package com.example.passhelp.dao

import android.content.ContentValues
import android.content.Context
import com.example.passhelp.database.DBContract
import com.example.passhelp.database.DBHelper
import com.example.passhelp.exceptions.ValidationErrorException
import com.example.passhelp.model.GroupModel
import com.example.passhelp.model.SecretsModel

class GroupDao(context:Context) {
    private val dbHelper = DBHelper(context)


    fun createGroup(group:GroupModel):Long{
        val db =dbHelper.writableDatabase;
        val values =ContentValues().apply {
            put(DBContract.GroupSecrets.COL_NAME,group.name)
            put(DBContract.GroupSecrets.COL_DESCRIPTION,group.description)
            put(DBContract.GroupSecrets.COL_AUTHOR_ID,group.authorId)
        }
        val id = db.insert(DBContract.GroupSecrets.TABLE_NAME,null,values);
        db.close()
        return id;
    }
    fun filterGroups(groupFiltro:GroupModel):MutableList<GroupModel>{
        val db =dbHelper.readableDatabase
        val columns = arrayOf(
            DBContract.GroupSecrets.COL_ID,
            DBContract.GroupSecrets.COL_NAME,
            DBContract.GroupSecrets.COL_AUTHOR_ID,
            DBContract.GroupSecrets.COL_DESCRIPTION
        )
        var select = "1 = 1";
        val selectArgs = arrayOf<String>();
        if(!(groupFiltro.authorId>0)){
            throw ValidationErrorException("Usuario deve ser informado")
        }
        select+= " and ${DBContract.GroupSecrets.COL_AUTHOR_ID} = ${groupFiltro.authorId}"
        if(groupFiltro.name.isNotEmpty()){
            select+= " and ${DBContract.GroupSecrets.COL_NAME} like %${groupFiltro.name}%"
        }
        val cursor = db.query(
            DBContract.GroupSecrets.TABLE_NAME,
            columns,
            select,
            selectArgs,
            null,null,null
        );

        val list = mutableListOf<GroupModel>();
        if(cursor.count>0){
            cursor.moveToFirst()
            do{
                val idIndex = cursor.getColumnIndex(DBContract.GroupSecrets.COL_ID)
                val nameIndex = cursor.getColumnIndex(DBContract.GroupSecrets.COL_NAME)
                val descIndex = cursor.getColumnIndex(DBContract.GroupSecrets.COL_DESCRIPTION)
                val authorIndex =  cursor.getColumnIndex(DBContract.GroupSecrets.COL_AUTHOR_ID)


                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val description = cursor.getString(descIndex)
                val authorId = cursor.getLong(authorIndex)

                list.add(
                    GroupModel(
                    id,name,description,authorId
                )
                )
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list;

    }
}