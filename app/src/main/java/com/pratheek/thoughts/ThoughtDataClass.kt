package com.pratheek.thoughts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ThoughtDataClass(
    var thoughtContent: String,

    var thoughtTextStyle: Int,

    var thoughtCardBackgroundColor: Int,

    var thoughtTextColor: String
) : Parcelable