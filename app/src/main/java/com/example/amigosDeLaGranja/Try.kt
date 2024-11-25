package com.example.tutorialfarm

import android.os.Parcel
import android.os.Parcelable

class Try(val time: Int, val errors: Int, val game: String, val date: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(time)
        parcel.writeInt(errors)
        parcel.writeString(game)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Try> {
        override fun createFromParcel(parcel: Parcel): Try {
            return Try(parcel)
        }

        override fun newArray(size: Int): Array<Try?> {
            return arrayOfNulls(size)
        }
    }
}
