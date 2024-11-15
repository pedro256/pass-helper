package com.example.passhelp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USERS)
        db.execSQL(SQL_CREATE_TABLE_PASSWORD)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_TABLE_USERS)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PassHelper.db"
    }
}

private const val SQL_CREATE_TABLE_USERS =
    "CREATE TABLE IF NOT EXISTS ${DBContract.Users.TABLE_NAME} (" +
            "${DBContract.Users.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${DBContract.Users.COL_NAME} VARCHAR(100)," +
            "${DBContract.Users.COL_USERNAME} VARCHAR(100)," +
            "${DBContract.Users.COL_PASSWORD} VARCHAR(100)" +
            ");"
private const val SQL_CREATE_TABLE_PASSWORD = """
            CREATE TABLE ${DBContract.Secrets.TABLE_NAME} (
                ${DBContract.Secrets.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${DBContract.Secrets.COL_USERNAME} VARCHAR(100),
                ${DBContract.Secrets.COL_TITLE} VARCHAR(250),
                ${DBContract.Secrets.COL_PASSWORD} VARCHAR(150),
                ${DBContract.Secrets.COL_COMPLEMENT}  TEXT NOT NULL,
                ${DBContract.Secrets.COL_USER_ID}  INTEGER NOT NULL,
                FOREIGN KEY(${DBContract.Secrets.COL_USER_ID}) REFERENCES ${DBContract.Users.TABLE_NAME}(${DBContract.Users.COL_ID})
                ON DELETE CASCADE
            )
        """


private const val SQL_DELETE_TABLE_USERS = "DROP TABLE IF EXISTS ${DBContract.Users.TABLE_NAME};"