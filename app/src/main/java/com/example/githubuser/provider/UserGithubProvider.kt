package com.example.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuser.db.DatabaseContract.AUTHORITY
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.example.githubuser.db.UserHelper

class UserGithubProvider : ContentProvider() {

    companion object{
        private const val USER_FAV= 1
        private const val USER_FAV_ID= 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userHelper: UserHelper

        init {
            uriMatcher.addURI(AUTHORITY,TABLE_NAME, USER_FAV)

            uriMatcher.addURI(AUTHORITY,"$TABLE_NAME/#", USER_FAV_ID)
        }

    }
    override fun onCreate(): Boolean {
        userHelper = UserHelper.getInstance(context as Context)
        userHelper.open()
        return true
    }

    override fun query(uri: Uri, string: Array<String>?, s: String?, string1: Array<String>?, s1: String?): Cursor? {
        return when(uriMatcher.match(uri)){
            USER_FAV-> userHelper.queryyAll()
            USER_FAV_ID-> userHelper.queryById(uri.lastPathSegment.toString())
            else-> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }


    override fun insert(uri: Uri, contentValue: ContentValues?): Uri? {
        val added:Long = when(USER_FAV){
            uriMatcher.match(uri)-> userHelper.insert(contentValue)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return Uri.parse("$CONTENT_URI/$added")
    }


    override fun update(uri: Uri, contentValue: ContentValues?, s: String?, string: Array<String>?): Int {
       val updated: Int= when(USER_FAV_ID){
            uriMatcher.match(uri) -> userHelper.update(uri.lastPathSegment.toString(),contentValue)
           else -> 0
       }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
       val delete: Int = when(USER_FAV_ID){
           uriMatcher.match(uri)-> userHelper.deleteById(uri.lastPathSegment.toString())
           else->0
       }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return delete
    }


}