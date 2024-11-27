package com.example.amigosDeLaGranja

import android.os.Parcel
import android.os.Parcelable

class Try(val modo: String, val fecha: String,val errores: Int, val tiemposAnimales: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tiemposAnimales)
        parcel.writeInt(errores)
        parcel.writeString(modo)
        parcel.writeString(fecha)
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
