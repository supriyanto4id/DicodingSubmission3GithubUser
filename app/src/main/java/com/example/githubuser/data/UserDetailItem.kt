package com.example.githubuser.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailItem (
    var nameLogin : String? = null,
    var imgAvatar : String? = null,
    var company : String? = null,
    var linkBlog : String? = null,
    var location : String? = null
):Parcelable