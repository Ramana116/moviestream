package com.sdp.movietime.DataClass

import android.os.Parcel
import android.os.Parcelable

data class RMovie(
    val name: String,
    val imageRes: Int,
    val description: String,
    val genres: String,
    val trailerResId: Int = 0,
    var isWishlisted: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imageRes)
        parcel.writeString(description)
        parcel.writeString(genres)
        parcel.writeInt(trailerResId)
        parcel.writeByte(if (isWishlisted) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RMovie> {
        override fun createFromParcel(parcel: Parcel): RMovie {
            return RMovie(parcel)
        }

        override fun newArray(size: Int): Array<RMovie?> {
            return arrayOfNulls(size)
        }
    }
}
