package com.example.amigosDeLaGranja

import android.os.Parcel
import android.os.Parcelable

class Player(var nombre: String, val partidas: MutableList<Try>) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        mutableListOf<Try>().apply {
            parcel.readList(this, Try::class.java.classLoader)
        }
    )

    fun addTry(newTry: Try) {
        this.partidas.add(newTry)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeList(partidas)
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
