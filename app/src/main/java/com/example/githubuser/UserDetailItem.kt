package com.example.githubuser

import android.os.Parcel
import android.os.Parcelable

data class UserDetailItem (
    var nameLogin : String? = null,
    var imgAvatar : String? = null,
    var company : String? = null,
    var linkBlog : String? = null,
    var location : String? = null
):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nameLogin)
        parcel.writeString(imgAvatar)
        parcel.writeString(company)
        parcel.writeString(linkBlog)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDetailItem> {
        override fun createFromParcel(parcel: Parcel): UserDetailItem {
            return UserDetailItem(parcel)
        }

        override fun newArray(size: Int): Array<UserDetailItem?> {
            return arrayOfNulls(size)
        }
    }

}