package com.example.githubuser.data

import android.os.Parcel
import android.os.Parcelable


data class Favorite (
    var _id: Int?= null,
    var username: String? = null,
    var avatar: String? =null,
    var company: String? =null,
    var url_web: String? = null,
    var location: String?= null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_id!!)
        parcel.writeString(username)
        parcel.writeString(avatar)
        parcel.writeString(company)
        parcel.writeString(url_web)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Favorite> {
        override fun createFromParcel(parcel: Parcel): Favorite {
            return Favorite(parcel)
        }

        override fun newArray(size: Int): Array<Favorite?> {
            return arrayOfNulls(size)
        }
    }
}