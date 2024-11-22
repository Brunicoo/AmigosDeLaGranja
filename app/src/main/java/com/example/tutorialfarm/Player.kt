package com.example.tutorialfarm

import android.os.Parcel
import android.os.Parcelable

class Player(var name: String, val tries: MutableList<Try>) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        mutableListOf<Try>().apply {
            parcel.readList(this, Try::class.java.classLoader)
        }
    )

    fun addTry(newTry: Try) {
        this.tries.add(newTry)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(tries)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}
