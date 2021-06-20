package com.example.githubuser

import android.os.Parcel
import android.os.Parcelable

class UserItem (
    var nameLogin : String? = null,
    var imgAvatar : String? = null

) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(nameLogin)
        parcel.writeString(imgAvatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserItem> {
        override fun createFromParcel(parcel: Parcel): UserItem {
            return UserItem(parcel)
        }

        override fun newArray(size: Int): Array<UserItem?> {
            return arrayOfNulls(size)
        }
    }
}
