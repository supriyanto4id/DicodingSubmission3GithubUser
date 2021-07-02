package com.example.githubuser.db

import android.net.Uri
import android.provider.BaseColumns


object DatabaseContract {

    const val AUTHORITY = "com.example.githubuser"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "user_github"
            const val _ID ="_id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"
            const val COMPANY ="company"
            const val BLOG_URL ="blog_url"
            const val LOCATION ="location"

            // untuk membuat URI content://com.example.githubuser/user_github
            val CONTENT_URI:Uri=Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }

}