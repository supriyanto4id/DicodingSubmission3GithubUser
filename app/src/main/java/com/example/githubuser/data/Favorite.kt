package com.example.githubuser.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Favorite (
    var _id: Int?= null,
    var username: String? = null,
    var avatar: String? =null,
    var company: String? =null,
    var url_web: String? = null,
    var location: String?= null
): Parcelable