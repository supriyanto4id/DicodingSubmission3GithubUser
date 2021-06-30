package com.example.githubuser.data

import android.os.Parcel
import android.os.Parcelable

class FollowersFollowingItem(
        var nameLogin : String? = null,
        var imgAvatar : String? = null,
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nameLogin)
        parcel.writeString(imgAvatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FollowersFollowingItem> {
        override fun createFromParcel(parcel: Parcel): FollowersFollowingItem {
            return FollowersFollowingItem(parcel)
        }

        override fun newArray(size: Int): Array<FollowersFollowingItem?> {
            return arrayOfNulls(size)
        }
    }
}