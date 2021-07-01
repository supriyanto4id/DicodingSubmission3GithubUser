package com.example.githubuser.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserItem (
    var nameLogin : String? = null,
    var imgAvatar : String? = null
):Parcelable
