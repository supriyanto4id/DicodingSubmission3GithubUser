package com.example.githubuser.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "user_github"
            const val _ID ="_id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"
            const val COMPANY ="company"
            const val BLOG_URL ="blog_url"
            const val LOCATION ="location"
        }
    }
}