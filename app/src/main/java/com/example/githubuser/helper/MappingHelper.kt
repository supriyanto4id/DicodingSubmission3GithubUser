package com.example.githubuser.helper

import android.database.Cursor
import android.provider.ContactsContract
import com.example.githubuser.data.Favorite
import com.example.githubuser.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<Favorite>{
        val userList= ArrayList<Favorite>()
        userCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
                val urlWeb = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.BLOG_URL))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                userList.add(
                        Favorite(
                                id,
                                username,
                                avatar,
                                company,
                                urlWeb,
                                location
                        )
                )
            }
        }

        return userList
    }


}