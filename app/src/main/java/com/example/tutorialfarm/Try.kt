package com.example.tutorialfarm

import android.os.Parcel
import android.os.Parcelable

class Try(val time: List<Int>, val errors: Int, val game: String, val date: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.createIntArray()?.toList() ?: listOf(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(time.toIntArray())
        parcel.writeInt(errors)
        parcel.writeString(game)
        parcel.writeLong(date)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Try> {
        override fun createFromParcel(parcel: Parcel): Try {
            return Try(parcel)
        }

        override fun newArray(size: Int): Array<Try?> {
            return arrayOfNulls(size)
        }
    }
}
