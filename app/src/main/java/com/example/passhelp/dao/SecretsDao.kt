package com.example.passhelp.dao

import android.content.ContentValues
import android.content.Context
import com.example.passhelp.database.DBContract
import com.example.passhelp.database.DBHelper
import com.example.passhelp.model.SecretsModel

class SecretsDao(context:Context) {
    private val dbHelper = DBHelper(context)

    fun listAllByUserId(idUser:Int):MutableList<SecretsModel>{
        val db =dbHelper.readableDatabase;
        val columns = arrayOf(
            DBContract.Secrets.COL_ID,
            DBContract.Secrets.COL_USERNAME,
            DBContract.Secrets.COL_TITLE,
            DBContract.Secrets.COL_PASSWORD,
            DBContract.Secrets.COL_COMPLEMENT
        )
        val select = "${DBContract.Secrets.COL_USER_ID} = ?"
        val selectArgs = arrayOf(
            idUser.toString()
        )
        val cursor = db.query(
            DBContract.Secrets.TABLE_NAME,
            columns,
            select,
            selectArgs,
            null,null,null
        )
        val list = mutableListOf<SecretsModel>();
        if(cursor.count>0){
            cursor.moveToFirst()
            do{
                val idIndex = cursor.getColumnIndex(DBContract.Secrets.COL_ID)
                val titleIndex = cursor.getColumnIndex(DBContract.Secrets.COL_TITLE)
                val userNameIndex = cursor.getColumnIndex(DBContract.Secrets.COL_USERNAME)
                val passwordIndex =  cursor.getColumnIndex(DBContract.Secrets.COL_PASSWORD)
                val detailsIndex =  cursor.getColumnIndex(DBContract.Secrets.COL_COMPLEMENT)


                val id = cursor.getInt(idIndex)
                val title = cursor.getString(titleIndex)
                val username = cursor.getString(userNameIndex)
                val pass = cursor.getString(passwordIndex)
                val complemt = cursor.getString(detailsIndex)

                list.add(SecretsModel(
                    id,
                    title,
                    username,
                    pass,
                    complemt,
                    idUser
                ))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list;
    }

    fun createSecret(secret:SecretsModel):Long{
        val db =dbHelper.writableDatabase;
        val values = ContentValues().apply {
            put(DBContract.Secrets.COL_TITLE,secret.title)
            put(DBContract.Secrets.COL_USERNAME,secret.username)
            put(DBContract.Secrets.COL_PASSWORD,secret.password)
            put(DBContract.Secrets.COL_COMPLEMENT,secret.complement)
            put(DBContract.Secrets.COL_USER_ID,secret.userId)
        }
        val id = db.insert(DBContract.Secrets.TABLE_NAME,null,values)
        db.close()
        return id;
    }
    fun updateSecret(secret:SecretsModel):Long{
        val db =dbHelper.writableDatabase;
        val values = ContentValues().apply {
            put(DBContract.Secrets.COL_TITLE,secret.title)
            put(DBContract.Secrets.COL_USERNAME,secret.username)
            put(DBContract.Secrets.COL_PASSWORD,secret.password)
            put(DBContract.Secrets.COL_COMPLEMENT,secret.complement)
        }
        val id = db.update(
            DBContract.Secrets.TABLE_NAME,
            values,
            "${DBContract.Secrets.COL_ID} = ?",
            arrayOf(secret.id.toString())
        )
        db.close()
        return secret.id.toLong();
    }

    fun deleteSecret(id:Int):Boolean{
        val db = dbHelper.writableDatabase;
        val selectArgs = arrayOf(id.toString())
        db.delete(
            DBContract.Secrets.TABLE_NAME,
            "${DBContract.Secrets.COL_ID} = ?",
            selectArgs
        )
        db.close()
        return true;
    }
}