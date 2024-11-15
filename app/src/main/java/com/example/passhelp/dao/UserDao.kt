package com.example.passhelp.dao

import android.content.ContentValues
import android.content.Context
import com.example.passhelp.database.DBContract
import com.example.passhelp.database.DBHelper
import com.example.passhelp.model.UserModel

class UserDao (context: Context) {
    private val dbHelper = DBHelper(context)
    
    data class AuthResponse(val authenticated:Boolean,val id:Int=0,val message:String?)

    fun insertUser(user:UserModel): Long {
        val db =dbHelper.writableDatabase;
        val values =ContentValues().apply {
            put(DBContract.Users.COL_NAME,user.Name)
            put(DBContract.Users.COL_USERNAME,user.Username)
            put(DBContract.Users.COL_PASSWORD,user.Pssd)
        }
        val id = db.insert(DBContract.Users.TABLE_NAME,null,values)
        db.close()
        return id;
    }

    fun authenticateUser(user: UserModel):AuthResponse{
        val db =dbHelper.readableDatabase;

        val columns = arrayOf(
            DBContract.Users.COL_ID,
            DBContract.Users.COL_NAME,
            DBContract.Users.COL_PASSWORD,
            DBContract.Users.COL_USERNAME
            )
        val selection = "${DBContract.Users.COL_USERNAME} = ?"
        val selectionArgs = arrayOf(user.Username)
        val cursor = db.query(
            DBContract.Users.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,null,null
        )
        var isAuthenticated =false
        var id = 0
        var msg ="";

        if(cursor.count > 0){
            cursor.moveToFirst()
            val colPassword = cursor.getColumnIndex(DBContract.Users.COL_PASSWORD)
            val colId =cursor.getColumnIndex(DBContract.Users.COL_ID)
            val psd = cursor.getString(colPassword);
            id = cursor.getInt(colId);

            if(psd == user.Pssd){
                isAuthenticated = true;
                msg="Autenticado com sucesso !"
            }else{
                msg = "Dados incorretos !"
            }
        }else{
            msg = "Usuário não encontrado"
        }
        cursor.close()
        db.close()
        return AuthResponse(
            authenticated = isAuthenticated,
            id= id,
            message = msg
        )
    }

    // Função para consultar usuários
    fun getAllUsers(): List<UserModel> {
        // Lógica para consultar todos os usuários no banco de dados
        return ArrayList<UserModel>();
    }
}