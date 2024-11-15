package com.example.passhelp.database

object DBContract {
    const val SCHEMA = "public"

    object Users{
        const val TABLE_NAME ="users"
        const val COL_ID="id"
        const val COL_NAME="name"
        const val COL_USERNAME="username"
        const val COL_PASSWORD="password"
    }
    object Secrets{
        const val TABLE_NAME="secrets"
        const val COL_ID="id"
        const val COL_TITLE="title"
        const val COL_USERNAME="username"
        const val COL_PASSWORD="password"
        const val COL_COMPLEMENT="complement"
        const val COL_USER_ID="user_id"
    }


}