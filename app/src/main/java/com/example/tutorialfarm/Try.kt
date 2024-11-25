package com.example.tutorialfarm

import android.os.Parcel
import android.os.Parcelable

class Try(val time: Int, val errors: Int, val difficulty: String, val date: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
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
