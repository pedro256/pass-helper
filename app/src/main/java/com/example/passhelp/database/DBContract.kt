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
    object GroupSecrets{
        const val TABLE_NAME="secrets_group"
        const val COL_ID="id"
        const val COL_NAME="name"
        const val COL_DESCRIPTION="description"
        const val COL_AUTHOR_ID="auth_id"
    }

    object GroupToSecret{
        const val TABLE_NAME="group_to_secret"
        const val COL_ID="id"
        const val COL_SECRET_ID="secret_id"
        const val COL_GROUP_ID="group_id"
    }

}